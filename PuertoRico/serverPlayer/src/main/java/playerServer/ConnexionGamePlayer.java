package playerServer;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import servercommon.Connexion;
import puertoRico.player.Player;
import puertoRico.characters.Character;

public class ConnexionGamePlayer extends Connexion {

    private ControllerPlayer controllerPlayer;

    public ConnexionGamePlayer(String hostAddress, int port) {
        super(hostAddress, port);

        socketClient.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {




            }
        });

        socketClient.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                socketClient.disconnect();
                socketClient.off();
                socketClient.close();
                socketClient.emit(Socket.EVENT_DISCONNECT);
                System.exit(0);
            }
        });

        socketClient.on("init", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                int nbPlayers = (int) objects[0];
                controllerPlayer.receiveInit(nbPlayers);
            }
        });

        socketClient.on("currentPlayerTurn", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {

                controllerPlayer.sendGouvernorPlayTurn(objects[0]);
            }
        });

        socketClient.on("playTurn", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                controllerPlayer.receivePlayTurn(objects[0]);

            }
        });

        socketClient.on("gameFinished", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Boolean gameFinished = (Boolean) objects[0];
                controllerPlayer.receiveIsGameFinished(gameFinished);
            }
        });

        socketClient.on("winner", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                controllerPlayer.receiveWinner();
            }
        });

    }






    @Override
    public void connectingToServer() {
        super.connectingToServer();
    }

    public void setControllerPlayer(ControllerPlayer controllerPlayer) {
        this.controllerPlayer = controllerPlayer;
    }

    public ControllerPlayer getControllerPlayer() {
        return controllerPlayer;
    }

    public void emitTurnPlayed(String characterChoosed){
        socketClient.emit("playTurn",characterChoosed);
    }

    public void emitIsGameFinished(boolean hasFullCity){
        socketClient.emit("gameFinished",hasFullCity);
    }

    public void emitTurnGovernorFinished(Character characterChoosed){
        socketClient.emit("turnGovernorFinished",characterChoosed.serialize());
    }
    public void emitNumberPlayers(){

        socketClient.emit("init");
    }

    public void emitWinner(Player player) throws JSONException {
        socketClient.emit("winner",player.convertToJson());
    }
    public void emitReady(String player) {
        socketClient.emit("ready",player);
    }



}
