package edu.byu.cs.tweeter.server;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import edu.byu.cs.tweeter.server.factory.DAOFactory;
import edu.byu.cs.tweeter.server.factory.DynamoDBFactory;

public class HandlerConfig {
    // Is a singleton
    private static HandlerConfig config;
    private static DynamoDBFactory factory;

    private HandlerConfig() {}

    public static HandlerConfig getInstance() {
        if(config == null) {
            config = new HandlerConfig();
        }
        return config;
    }

    public DAOFactory getFactory() {
        if (factory == null) {
            factory = new DynamoDBFactory();
        }
        return factory;
    }
}
