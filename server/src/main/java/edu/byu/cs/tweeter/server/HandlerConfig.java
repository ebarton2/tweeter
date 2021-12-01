package edu.byu.cs.tweeter.server;

import edu.byu.cs.tweeter.server.factory.DAOFactory;
import edu.byu.cs.tweeter.server.factory.DynamoDBFactory;

public class HandlerConfig {
    // Is a singleton
    private static HandlerConfig config;

    private HandlerConfig() {}

    public static HandlerConfig getInstance() {
        if(config == null) {
            config = new HandlerConfig();
        }
        return config;
    }

    public DAOFactory getFactory() { return new DynamoDBFactory(); }
}
