package playerServer;
import com.corundumstudio.socketio.Configuration;
import config.ConfigPlayer;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;


public class ServerPlayer {

    ConnexionServerPlayer connexionServerPlayer;

    public ServerPlayer(ConnexionServerPlayer connexionServerGame) {
        this.connexionServerPlayer = connexionServerGame;
    }


    public ConnexionServerPlayer getConnexionServerPlayer() {
        return connexionServerPlayer;
    }

    public static final void main(String []args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Configuration configPlayer = new Configuration();
        configPlayer.setHostname(ConfigPlayer.hostAddress);
        configPlayer.setPort(ConfigPlayer.port);

        ConnexionServerPlayer connexionServerPlayer =new ConnexionServerPlayer(configPlayer);

        ServerPlayer server = new ServerPlayer(connexionServerPlayer);
        server.getConnexionServerPlayer().demarrer();




    }

}