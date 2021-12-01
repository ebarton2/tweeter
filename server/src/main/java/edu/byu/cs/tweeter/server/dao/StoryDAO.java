package edu.byu.cs.tweeter.server.dao;

import java.text.ParseException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;

public interface StoryDAO {
    List<Status> getStory(String alias, Status lastStatus, int limit) throws ParseException;
    boolean getPostStatus(String alias, Status status, List<String> people) throws ParseException;
}
