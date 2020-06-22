package Statistics;

import gameServer.ControllerStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import gameServer.ConnexionStats;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;


public class SendingMessageTest {

    @Mock
    private ConnexionStats connexionStats;
    @BeforeEach
    public void setUp(){

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void sendMessageTest(){
        ControllerStats controllerStats = new ControllerStats();
        String message = "Hi";

        doAnswer(new Answer(){


            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {

                controllerStats.setMessageHasBeenSent(true);
                return null;

            }
        }).when(connexionStats).emitMessage(message);

        connexionStats.emitMessage(message);

        assertEquals(true, controllerStats.isMessageHasBeenSent());

    }
}
