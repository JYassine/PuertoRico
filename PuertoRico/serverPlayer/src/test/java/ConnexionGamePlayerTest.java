import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import playerServer.ConnexionGamePlayer;

import static org.mockito.Mockito.mock;

public class ConnexionGamePlayerTest {

    private ConnexionGamePlayer connexionGamePlayer;
    private int numberPlayers=10;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        connexionGamePlayer = mock(ConnexionGamePlayer.class);
    }
/*
    @Test
    public void testConnexionPlayers(){

        for(int i=0;i<numberPlayers;i++){
            Player p = new Player("joueur "+i,2);
            ControllerPlayer controllerPlayer = new ControllerPlayer();
            controllerPlayer.setPlayer(p);
            controllerPlayer.setLevel(Level.FINE);
            controllerPlayer.setConnexionGame(connexionGame);
            doAnswer(new Answer() {
                @Override
                public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                    controllerPlayer.receiveConnexionToGame();
                    return null;
                }


            }).when(connexionGame).emitConnectToGame(any(JSONObject.class));
            //controllerPlayer.sendConnexionToGame();
        }

        verify(connexionGame, times(10)).emitConnectToGame(any(JSONObject.class));

}*/
}
