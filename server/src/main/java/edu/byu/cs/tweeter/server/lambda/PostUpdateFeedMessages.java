package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import edu.byu.cs.tweeter.server.HandlerConfig;
import edu.byu.cs.tweeter.server.factory.DAOFactory;
import edu.byu.cs.tweeter.server.service.FollowService;

public class PostUpdateFeedMessages implements RequestHandler<SQSEvent, Void> {


    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        for (SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {
            String alias = msg.getMessageAttributes().get("alias").getStringValue();
            int limit = Integer.parseInt(msg.getMessageAttributes().get("limit").getStringValue());
            long epoch = Long.parseLong(msg.getMessageAttributes().get("time_stamp").getStringValue());
            String statusJSON = msg.getBody();

            System.out.println(alias);
            System.out.println(limit);
            System.out.println(statusJSON);

            DAOFactory factory = HandlerConfig.getInstance().getFactory();
            FollowService followService = new FollowService(factory);
            followService.updateFollowersFeedSQS(alias, limit, epoch, statusJSON);
        }
        return null;
    }


}
