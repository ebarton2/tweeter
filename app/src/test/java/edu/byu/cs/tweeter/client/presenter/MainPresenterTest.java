package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;

public class MainPresenterTest {

    private MainPresenter.MainView mockMainVew;
    private UserService mockUserService;
    private StatusService mockStatusService;
    private Cache mockCache;

    private MainPresenter mainPresenterSpy;

    @BeforeEach
    public void setup() {
        //Create mock dependencies
        mockMainVew = Mockito.mock(MainPresenter.MainView.class);
        mockUserService = Mockito.mock(UserService.class);
        mockStatusService = Mockito.mock(StatusService.class);
        mockCache = Mockito.mock(Cache.class);

        mainPresenterSpy = Mockito.spy(new MainPresenter(mockMainVew));
        Mockito.doReturn(mockUserService).when(mainPresenterSpy).getUserService();
        Mockito.doReturn(mockStatusService).when(mainPresenterSpy).getStatusService();

        Cache.setInstance(mockCache);
    }

    @Test
    public void testPostStatus_postSuccess() {
        Answer<Void> successAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StatusService.PostStatusObserver observer = invocation.getArgumentAt(1, StatusService.PostStatusObserver.class);
                observer.handleSuccess();
                return null;
            }
        };

        Mockito.doAnswer(successAnswer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any());

        mainPresenterSpy.postStatus("Test");

        Mockito.verify(mockMainVew).infoMessage("Posting Status...");
        Mockito.verify(mockMainVew).clearInfoMessage();
        Mockito.verify(mockMainVew).infoMessage("Successfully Posted!");
    }

    @Test
    public void testPostStatus_postFail() {
        Answer<Void> failureAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StatusService.PostStatusObserver observer = invocation.getArgumentAt(1, StatusService.PostStatusObserver.class);
                observer.handleFailure("TestFailure");
                return null;
            }
        };

        Mockito.doAnswer(failureAnswer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any());

        mainPresenterSpy.postStatus("Test");

        Mockito.verify(mockMainVew).infoMessage("Posting Status...");
        Mockito.verify(mockMainVew).infoMessage("TestFailure");
    }
}
