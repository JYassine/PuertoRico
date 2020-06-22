package servercommon;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

public abstract class Connexion {

    protected String hostAddress;
    protected int port;
    protected Socket socketClient;


    public Connexion(String hostAddress, int port){
        this.hostAddress=hostAddress;
        this.port=port;
        try {
            socketClient = IO.socket("http://" + this.hostAddress + ":" + port);
        } catch (URISyntaxException e) {

            e.printStackTrace();
        }

    }

    public Socket getSocketClient() {
        return socketClient;
    }

    public void connectingToServer(){

        socketClient.connect();
    }

}
