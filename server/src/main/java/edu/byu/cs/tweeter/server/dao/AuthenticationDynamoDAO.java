package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import java.time.Instant;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class AuthenticationDynamoDAO extends DynamoDBDAO implements AuthenticationDAO {

    private static final long EXPIRATION_TIME = 900; // 15 minutes currently

    public AuthToken createAuthToken(String alias, AuthToken authToken) {
        Table table = getTable(AUTH_TABLE);
        long time = Long.parseLong(authToken.getDatetime());

        PutItemOutcome outcome = table.putItem(new Item()
                .withPrimaryKey("auth_token",authToken.getToken())
                .withString("alias", alias)
                .withLong("timeStamp", time)
                .withLong("expiration", time + EXPIRATION_TIME));
        return authToken;
    }

    @Override
    public String getAliasFromToken(String token) throws Exception {
        Item item = getItem(token);
        String alias = item.getString("alias");
        return alias;
    }

    @Override
    public boolean isAuthorized(String authToken) throws Exception {
        Item item = getItem(authToken);
        if (item != null) {
            return true;
        }
        return false;
    }

    public boolean logout(String token) throws Exception {
        Table table = getTable(AUTH_TABLE);
        DeleteItemSpec spec = new DeleteItemSpec().withPrimaryKey("auth_token", token);
        table.deleteItem(spec);
        return true;
    }

    private Item getItem(String token) {
        Table table = getTable(AUTH_TABLE);
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("auth_token", token);
        Item item = table.getItem(spec);
        return item;
    }
}
