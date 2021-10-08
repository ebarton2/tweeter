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
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observers.AuthenticationServiceObserver;
import edu.byu.cs.tweeter.client.view.interfaces.AuthenticationViewInterface;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter extends AuthenticationAbstractPresenter<RegisterPresenter.RegisterView>
{
    public interface RegisterView extends AuthenticationViewInterface {}

    public RegisterPresenter(RegisterView view)
    {
        super(view);
    }

    public void register(String firstName, String lastName,
                         String alias, String password, ImageView image)
    {
        String validation = validate(firstName, lastName, alias, password, image);
        if(validation == null) {
            view.setErrorMessage(validation);
            view.infoMessage("Registering...");

            // Convert image to byte array.
            Bitmap imageBtmp = ((BitmapDrawable) image.getDrawable()).getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imageBtmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] imageBytes = bos.toByteArray();
            String imageBytesBase64 = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

            userService.register(firstName, lastName, alias, password, imageBytesBase64, new UserService.RegisterObserver() {
                @Override
                public void handleSuccess(User user, AuthToken authToken) {
                    authenticateSuccess(user, authToken);
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
}