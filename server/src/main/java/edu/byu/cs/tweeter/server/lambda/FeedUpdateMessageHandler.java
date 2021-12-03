package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import edu.byu.cs.tweeter.server.HandlerConfig;
import edu.byu.cs.tweeter.server.factory.DAOFactory;
import edu.byu.cs.tweeter.server.service.StatusService;

public class FeedUpdateMessageHandler implements RequestHandler<SQSEvent, Void> {
    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        for (SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {
            String alias = msg.getMessageAttributes().get("alias").getStringValue();
            String statusJSON = msg.getMessageAttributes().get("status").getStringValue();
            long epoch = Long.parseLong(msg.getMessageAttributes().get("time_stamp").getStringValue());
            String people = msg.getBody();
            System.out.println(alias);
            System.out.println(epoch);
            System.out.println(people);

            DAOFactory factory = HandlerConfig.getInstance().getFactory();
            StatusService service = new StatusService(factory);
            service.updateFeedBatch(alias, epoch, statusJSON, people);
        }
        return null;
    }
}
