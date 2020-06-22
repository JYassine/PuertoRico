package gameServer;
import org.json.JSONException;
import puertoRico.player.Player;
import stats.InfoPlayer;

public class ControllerStats {

    public ConnexionStats getConnexionStats() {
        return connexionStats;
    }

    public ControllerGame getControllerGame() {
        return controllerGame;
    }

    public void setControllerGame(ControllerGame controllerGame) {
        this.controllerGame = controllerGame;
    }

    private ControllerGame controllerGame;

    private ConnexionStats connexionStats;
    private boolean messageHasBeenSent=false;
    private String messageStats="";
    private boolean messageStatsReceived=false;

    public ControllerStats(){
    }

    public ControllerStats(ConnexionStats connexionStats){
        this.connexionStats=connexionStats;
        connexionStats.connectingToServer();
    }


    public void sendNbMove(){
        connexionStats.emitNbMove();
    }

    public void setConnexionStats(ConnexionStats connexionStats) {
        this.connexionStats = connexionStats;
    }


    public void sendGameFinished(){
        connexionStats.emitGameFinished();

    }


    public void receiveMessageStats(String messageStats){
        this.messageStats=messageStats;
        System.out.println(messageStats);
        messageStatsReceived=true;
        controllerGame.sendDisconnectToAllPlayers();
        connexionStats.getSocketClient().disconnect();
    }


    public void sendInfoPlayer(Player p){
        try {
            connexionStats.emitInfoPlayer(p.convertToJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendInfoPlayer(InfoPlayer p){
            connexionStats.emitInfoPlayer(p);
    }

    public void sendNumberPlayer(int numberPlayer){
        connexionStats.emitNumberPlayer(numberPlayer);
    }

    public void sendNumberGame(int numberGame){
        connexionStats.emitNumberGame(numberGame);
    }

    public boolean isMessageHasBeenSent() {
        return messageHasBeenSent;
    }

    public void setMessageHasBeenSent(boolean messageHasBeenSent) {
        this.messageHasBeenSent = messageHasBeenSent;
    }


}