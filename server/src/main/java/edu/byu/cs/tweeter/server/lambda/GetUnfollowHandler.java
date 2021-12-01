package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;
import edu.byu.cs.tweeter.server.HandlerConfig;
import edu.byu.cs.tweeter.server.factory.DAOFactory;
import edu.byu.cs.tweeter.server.service.FollowService;

public class GetUnfollowHandler implements RequestHandler<UnfollowRequest, UnfollowResponse> {
    @Override
    public UnfollowResponse handleRequest(UnfollowRequest unfollowRequest, Context context) {
        DAOFactory factory = HandlerConfig.getInstance().getFactory();
        FollowService service = new FollowService(factory);
        return service.getUnfollow(unfollowRequest);
    }
}
