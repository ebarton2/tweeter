package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserDynamoDAO extends DynamoDBDAO implements UserDAO  {

    public User getUser(String alias) {
        Table table = getTable(USER_TABLE);
        System.out.println(alias);

        GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias", alias);
        Item outcome = table.getItem(spec);
        User user = new User(outcome.get("firstName").toString(), outcome.get("lastName").toString(),
                outcome.get("alias").toString(), outcome.get("imageURL").toString());
        return user;
    }

    public User login(String alias, String password) {
        Table table = getTable(USER_TABLE);

        GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias", alias);
        Item outcome = table.getItem(spec);

        String passed_word = hashPassword(password, outcome.get("salt").toString());
        if (passed_word.equals(outcome.get("hashPassword").toString())) {
            User user = new User(outcome.get("firstName").toString(), outcome.get("lastName").toString(),
                    outcome.get("alias").toString(), outcome.get("imageURL").toString());
            return user;
        }
        return null;
    }

    public User register(String username, String secPassword, String salt,
                         String firstName, String lastName,
                         String image) {

        String imageURL = setS3ImageFile(image, username);
        User user = new User(firstName, lastName, username, imageURL);
        Table table = getTable("users");

        PutItemOutcome outcome = table.putItem(new Item().
                withPrimaryKey("alias", user.getAlias())
                .withString("firstName", user.getFirstName())
                .withString("lastName", user.getLastName())
                .withString("imageURL", user.getImageUrl())
                .withString("hashPassword", secPassword)
                .withString("salt", salt)
                .withNumber("following_count", 0)
                .withNumber("followers_count", 0));
        return user;
    }

    public String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "FAILED TO HASH PASSWORD";
    }

    /**
     * Gets the count of users from the database that the user specified is following. The
     * current implementation uses generated data and doesn't actually access a database.
     *
     * @param follower the User whose count of how many following is desired.
     * @return said count.
     */
    public int getFolloweeCount(User follower) {
        // TODO: uses the dummy data.  Replace with a real implementation.
        assert follower != null;
        Table table = getTable(USER_TABLE);
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias", follower.getAlias());
        Item outcome = table.getItem(spec);
        return outcome.getInt("following_count");
    }

    public int getFollowerCount(User follower) {
        assert follower != null;
        Table table = getTable(USER_TABLE);
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias", follower.getAlias());
        Item outcome = table.getItem(spec);
        return outcome.getInt("followers_count");
    }

    public void updateFolloweeCount(String alias, boolean increase) throws Exception {
        String expression = "set following_count = following_count + :val";
        updateFollowCounts(alias, increase, expression);
    }

    public void updateFollowerCount(String alias, boolean increase) throws Exception {
        String expression = "set followers_count = followers_count + :val";
        updateFollowCounts(alias, increase, expression);
    }

    private void updateFollowCounts(String alias, boolean increase, String expression) throws Exception {
        assert alias != null;
        Table table = getTable(USER_TABLE);
        int counter = 1;
        if (!increase) counter = -1;
        UpdateItemSpec spec = new UpdateItemSpec()
                .withPrimaryKey("alias", alias)
                .withUpdateExpression(expression)
                .withValueMap(new ValueMap().withNumber(":val", counter))
                .withReturnValues(ReturnValue.UPDATED_NEW);
        UpdateItemOutcome outcome = table.updateItem(spec);
    }

    public Item getUserItem(String alias) {
        Table table = getTable(USER_TABLE);
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias", alias);
        Item outcome = table.getItem(spec);
        return outcome;
    }
}
