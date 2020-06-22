package server.stats;
import config.ConfigServer;
import com.corundumstudio.socketio.Configuration;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;


public class Server {

    ConnexionServerStats connexionServerStats;

    public Server(ConnexionServerStats connexionServerStats) {
        this.connexionServerStats = connexionServerStats;
    }


    public ConnexionServerStats getConnexionServerStats() {
        return connexionServerStats;
    }

    public static final void main(String []args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Configuration configStats = new Configuration();

        configStats.setHostname(ConfigServer.hostAddress);
        configStats.setPort(ConfigServer.port);

        ConnexionServerStats connexionServerStats =new ConnexionServerStats(configStats);

        Server server = new Server(connexionServerStats);
        server.getConnexionServerStats().demarrer();


    }

}