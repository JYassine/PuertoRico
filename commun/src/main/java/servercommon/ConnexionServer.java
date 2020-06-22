package servercommon;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

public abstract class ConnexionServer {


    protected Configuration config;
    protected SocketIOServer server;
    protected final Object attenteConnexion = new Object();

    public ConnexionServer(final Configuration config){
        this.config=config;
        server = new SocketIOServer(config);

        System.out.println("preparation du listener");

        server.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("connexion de "+socketIOClient.getRemoteAddress());

            }
        });


    }

    public void demarrer() {
        server.start();
        System.out.println("en attente de connexion");
        synchronized (attenteConnexion) {
            try {
                attenteConnexion.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("erreur dans l'attente");
            }
        }
        server.stop();

    }
}
