package playerServer;

import com.corundumstudio.socketio.Configuration;
import servercommon.ConnexionServer;


public class ConnexionServerPlayer extends ConnexionServer {

    private ControllerPlayer controllerPlayer;

    public ConnexionServerPlayer(final Configuration config) {
        super(config);
        controllerPlayer = new ControllerPlayer();



    }

    @Override
    public void demarrer(){
        super.demarrer();

    }


}
