package gameServer;
import config.ConfigGame;
import com.corundumstudio.socketio.Configuration;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;


public class ServerGame {

    ConnexionServerGame connexionServerGame;

    public ServerGame(ConnexionServerGame connexionServerGame) {
        this.connexionServerGame = connexionServerGame;
    }


    public ConnexionServerGame getConnexionServerGame() {
        return connexionServerGame;
    }

    public static final void main(String []args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Configuration configGame = new Configuration();
        configGame.setHostname(ConfigGame.hostAddress);
        configGame.setPort(ConfigGame.port);


        ConnexionServerGame connexionServerGame =new ConnexionServerGame(configGame);



        ServerGame server = new ServerGame(connexionServerGame);
        server.getConnexionServerGame().demarrer();




    }

}