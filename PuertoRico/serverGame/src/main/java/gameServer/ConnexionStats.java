package gameServer;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;
import servercommon.Connexion;
import stats.InfoPlayer;

public class ConnexionStats extends Connexion {

    public ControllerStats getControllerStats() {
        return controllerStats;
    }

    public void setControllerStats(ControllerStats controllerStats) {
        this.controllerStats = controllerStats;
    }

    private ControllerStats controllerStats;


    public ConnexionStats(String hostAddress, int port, final ControllerStats controllerStats) {
        super(hostAddress,port);
        this.controllerStats = controllerStats;
        this.controllerStats.setConnexionStats(this);
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
            }
        });

        socketClient.on("message", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                boolean messageHasBeenSent = (boolean)objects[0];
                controllerStats.setMessageHasBeenSent(messageHasBeenSent);

            }
        });


        socketClient.on("gameFinished", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                String s = (String) objects[0];
                controllerStats.receiveMessageStats(s);

            }
        });


    }

    public void emitMessage(String message){
        this.socketClient.emit("message",message);
        System.out.println("The message "+message+" has been emitted to the server");
    }

    public void emitNbMove()
    {
        this.socketClient.emit("nbMove",1);
    }

    public void emitNumberPlayer(int numberPlayer){
        this.socketClient.emit("numberPlayer",numberPlayer);
    }

    public void emitNumberGame(int numberGame){
        this.socketClient.emit("numberGame",numberGame);
    }


    public void emitGameFinished(){
        this.socketClient.emit("gameFinished","");
    }

    public void emitInfoPlayer(JSONObject infoPlayer) {
        this.socketClient.emit("infoPlayer",infoPlayer);
    }


    public void emitInfoPlayer(InfoPlayer infoPlayer) {
        try {
            this.socketClient.emit("infoPlayer",infoPlayer.convertToJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectingToServer(){

        System.out.println("Trying to connect to the stats server...");

        super.connectingToServer();
    }

    public void emitGameNoWinner(boolean gameNoWinner) {
        this.socketClient.emit("gameNoWinner",gameNoWinner);
    }
}
