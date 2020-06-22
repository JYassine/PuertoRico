package server.stats;
import action.ActionPlayer;
import com.corundumstudio.socketio.listener.DisconnectListener;
import servercommon.ConnexionServer;
import stats.InfoPlayer;
import stats.Statistics;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;


public class ConnexionServerStats extends ConnexionServer {

    private Statistics stats = new Statistics() ;

    private int maximumGame=0;


    public ConnexionServerStats(final Configuration config){
        super(config);


        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient socketIOClient) {

                    socketIOClient.sendEvent(ActionPlayer.DISCONNECT_GAME.getNameAction(),true);
                    System.exit(0);
            }
        });

        server.addEventListener("numberGame", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Integer numberGame, AckRequest ackRequest) throws Exception {
                stats.setNumberGame(numberGame);
                maximumGame=numberGame-1;
            }
        });

        server.addEventListener("numberPlayer", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Integer numberPlayer, AckRequest ackRequest) throws Exception {
                stats.setNumberPlayer(numberPlayer);
            }
        });


        server.addEventListener("gameFinished", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                    if(maximumGame==0 ){
                        socketIOClient.sendEvent("gameFinished",stats.toString());
                        System.out.println("Les statistiques ont été envoyées au client !");
                    }
                    maximumGame--;

            }
        });


        server.addEventListener("infoPlayer", InfoPlayer.class, new DataListener<InfoPlayer>() {
            @Override
            public void onData(SocketIOClient socketIOClient, InfoPlayer infoPlayer, AckRequest ackRequest) throws Exception {

                stats.save(maximumGame,infoPlayer);

                stats.getNamePlayers().add(infoPlayer.getNamePlayer());


            }
        });

        server.addEventListener("message", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                System.out.println("Hey I'm the server, I received the message : "+ s);
                socketIOClient.sendEvent("message",true);
            }
        });

        server.addEventListener("nbMove", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Integer integer, AckRequest ackRequest) throws Exception {

                stats.calculateNbMoveInGame(integer);
            }
        });


    }



}


