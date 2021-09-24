package edu.byu.cs.tweeter.client.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter
{
    private RegisterPresenter.RegisterView view;
    private UserService userService;

    public interface RegisterView
    {
        void infoMessage(String message);
        void clearInfoMessage();
        void setErrorMessage(String message);
        void navigateToUser(User user);
    }

    public RegisterPresenter(RegisterView view)
    {
        this.view = view;
        userService = new UserService();
    }

    public void register(String firstName, String lastName,
                         String alias, String password, ImageView image)
    {
        String validation = validateRegistration(firstName, lastName, alias, password, image);
        if(validation == null) {
            view.setErrorMessage(validation);
            view.infoMessage("Registering...");

            // Convert image to byte array.
            Bitmap imageBtmp = ((BitmapDrawable) image.getDrawable()).getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imageBtmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] imageBytes = bos.toByteArray();
            String imageBytesBase64 = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

            // Send register request.

            userService.register(firstName, lastName, alias, password, imageBytesBase64, new UserService.RegisterObserver() {
                @Override
                public void handleSuccess(User user, AuthToken authToken) {
                    Cache.getInstance().setCurrUser(user);
                    Cache.getInstance().setCurrUserAuthToken(authToken);
                    view.clearInfoMessage();
                    view.infoMessage("Hello" + Cache.getInstance().getCurrUser().getName());
                    view.navigateToUser(user);
                }

                @Override
                public void handleFailure(String message) {
                    view.infoMessage(message);
                }
            });
        } else {
            view.setErrorMessage(validation);
        }
    }

    public String validateRegistration(String firstName, String lastName,
                                       String alias, String password, ImageView image) {
        String validation = null;
        if (firstName.length() == 0) {
            validation = "First Name cannot be empty.";
        }
        if (lastName.length() == 0) {
            validation = "Last Name cannot be empty.";
        }
        if (alias.length() == 0) {
            validation = "Alias cannot be empty.";
        }
        if (alias.charAt(0) != '@') {
            validation = "Alias must begin with @.";
        }
        if (alias.length() < 2) {
            validation = "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            validation = "Password cannot be empty.";
        }

        if (image.getDrawable() == null) {
            validation = "Profile image must be uploaded.";
        }
        return validation;
    }
}
