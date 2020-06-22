package gameServer;
import action.ActionPlayer;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import servercommon.ConnexionServer;
import stats.InfoPlayer;

import java.util.concurrent.atomic.AtomicInteger;

public class ConnexionServerGame extends ConnexionServer {

    private GameEngine game ;

    public ControllerGame getControllerGame() {
        return controllerGame;
    }

    private ControllerGame controllerGame ;
    private AtomicInteger allDisconnected = new AtomicInteger(0);


    public ConnexionServerGame(final Configuration config) {
        super(config);

        controllerGame = new ControllerGame(game);

        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient socketIOClient) {
                System.out.println("deconnexion de "+socketIOClient.getRemoteAddress());
                if(allDisconnected.incrementAndGet() == controllerGame.getNbPlayers()){
                    System.exit(0);
                }

            }
        });

        server.addEventListener("init", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String newPlayer, AckRequest ackRequest) throws Exception {
                controllerGame.sendInit(socketIOClient);
            }
        });



        server.addEventListener("ready", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String newPlayer, AckRequest ackRequest) throws Exception {
                controllerGame.receiveReady(socketIOClient,newPlayer);
            }
        });

        server.addEventListener("playTurn", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String characterChoosed, AckRequest ackRequest) throws Exception {;
                controllerGame.receiveTurnPlayed(characterChoosed);

            }
        });

        server.addEventListener("turnGovernorFinished", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String characterChoosed, AckRequest ackRequest) throws Exception {
                controllerGame.receiveTurnGovernorFinished(characterChoosed);


            }
        });

        server.addEventListener("gameFinished", Boolean.class, new DataListener<Boolean>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Boolean hasFullCity, AckRequest ackRequest) throws Exception {
                controllerGame.receiveIsGameFinished(hasFullCity);
                boolean gameFinished = controllerGame.getGame().checkEnd() || controllerGame.isPlayerHasFullCity();
                socketIOClient.sendEvent(ActionPlayer.IS_GAME_FINISHED.getNameAction(),gameFinished);


            }
        });
        server.addEventListener("disconnect_game", Boolean.class, new DataListener<Boolean>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Boolean hasFullCity, AckRequest ackRequest) throws Exception {
                controllerGame.sendDisconnectToAllPlayers();

            }
        });

        server.addEventListener("winner", InfoPlayer.class, new DataListener<InfoPlayer>() {
            @Override
            public void onData(SocketIOClient socketIOClient, InfoPlayer player, AckRequest ackRequest) throws Exception {
                controllerGame.receiveWinner(player);
            }
        });

    }
}