package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.HandlerConfig;
import edu.byu.cs.tweeter.server.factory.DAOFactory;
import edu.byu.cs.tweeter.server.service.StatusService;

public class GetStoryHandler implements RequestHandler<StoryRequest, StoryResponse> {
    @Override
    public StoryResponse handleRequest(StoryRequest request, Context context) {
        DAOFactory factory = HandlerConfig.getInstance().getFactory();
        StatusService service = new StatusService(factory);
        return service.getStory(request);
    }
}
