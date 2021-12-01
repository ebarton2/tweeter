package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * A DAO for accessing 'following' data from the database.
 */
public class FollowDynamoDAO extends DynamoFollowDAO implements FollowDAO {
    /**
     * Gets the users from the database that the user specified in the request is following. Uses
     * information in the request object to limit the number of followees returned and to return the
     * next set of followees after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     */
    public List<String> getFollowees(String alias, String lastFollowee, int limit) throws Exception {
        return follows(alias, lastFollowee, limit, null, "follower_handle", "followee_handle");
    }

    public List<String> getFollowers(String userAlias, String lastFollower, int limit) throws Exception {
        Table table = getTable(FOLLOWS_TABLE);
        Index index = table.getIndex("follows_index");
        return follows(userAlias, lastFollower, limit, index, "followee_handle", "follower_handle");
    }


    public boolean getIsFollower(String alias, String potential) throws Exception {
        Table table = getTable(FOLLOWS_TABLE);
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("follower_handle", alias,
                "followee_handle", potential);
        try {
            Item outcome = table.getItem(spec);
            System.out.println("Outcome info for getIsFollower:");
            System.out.println(outcome.getString("follower_handle"));
            System.out.println(outcome.getString("followee_handle"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean getFollow(String alias, String toFollow) {
        Table table = getTable(FOLLOWS_TABLE);
        PutItemOutcome outcome = table.putItem(new Item()
                .withPrimaryKey("follower_handle", alias,
                        "followee_handle", toFollow));
        return true;
    }

    public boolean getUnfollow(String alias, String toUnfollow) throws Exception {
        Table table = getTable(FOLLOWS_TABLE);
        DeleteItemSpec spec = new DeleteItemSpec()
                .withPrimaryKey(
                        new PrimaryKey("follower_handle", alias,
                                "followee_handle", toUnfollow));
        table.deleteItem(spec);
        return false;
    }
}