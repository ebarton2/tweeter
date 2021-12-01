package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.FollowerCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowerCountResponse;
import edu.byu.cs.tweeter.server.HandlerConfig;
import edu.byu.cs.tweeter.server.factory.DAOFactory;
import edu.byu.cs.tweeter.server.service.FollowService;

public class GetFollowerCountHandler implements RequestHandler<FollowerCountRequest, FollowerCountResponse> {
    @Override
    public FollowerCountResponse handleRequest(FollowerCountRequest input, Context context) {
        DAOFactory factory = HandlerConfig.getInstance().getFactory();
        FollowService followService = new FollowService(factory);
        return followService.getFollowerCount(input);
    }
}
