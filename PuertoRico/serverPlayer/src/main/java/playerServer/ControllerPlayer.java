package playerServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.ConfigGame;
import org.json.JSONException;
import puertoRico.characters.Character;
import puertoRico.characters.Characters;
import puertoRico.player.Player;
import useful.GameMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerPlayer {

    private ConnexionGamePlayer connexionGamePlayer;
    private static Logger controllerPlayerLogger = Logger.getLogger(String.valueOf(ControllerPlayer.class));
    private Level level;
    private Player player;

    public Characters getCharactersList() {
        return charactersList;
    }

    public void setCharactersList(Characters charactersList) {
        this.charactersList = charactersList;
    }

    private Characters charactersList;

    public boolean isCanPlayTurn() {
        return canPlayTurn;
    }

    public void setCanPlayTurn(boolean canPlayTurn) {
        this.canPlayTurn = canPlayTurn;
    }

    private boolean canPlayTurn=true;


    public ControllerPlayer(){
        this.level=Level.INFO;
        this.connexionGamePlayer = new ConnexionGamePlayer(ConfigGame.hostAddress,ConfigGame.port);
        connexionGamePlayer.setControllerPlayer(this);
        this.connexionGamePlayer.connectingToServer();
        this.connexionGamePlayer.emitNumberPlayers();
        GameMessage.setLevel(Level.INFO);

    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public void sendReady(){
        this.connexionGamePlayer.emitReady(this.getPlayer().getName());
    }

    public void sendGouvernorPlayTurn(Object charactersObject){

        String charactersSerialized = (String) charactersObject;
        Characters characters = new Characters() ;
        ObjectMapper mapper = new ObjectMapper();

        try {
            characters = mapper.readValue(charactersSerialized, Characters.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        charactersList = characters;

        sendIsGameFinished();


    }

    public void sendWinner(){
        try {
            connexionGamePlayer.emitWinner(this.getPlayer());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void receiveWinner(){
        sendWinner();
    }
    public void receivePlayTurn( Object charactersObject){
        String characterSerialized = (String) charactersObject;
        Character character =null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            character = mapper.readValue(characterSerialized, Character.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        GameMessage.messageWhenCharacterChoosen(this.getPlayer(),character);
        this.getPlayer().playRole(character);
        this.connexionGamePlayer.emitTurnPlayed(character.serialize());

    }

    public void sendTurnGovernorFinished(Character characterChoosed){
        this.connexionGamePlayer.emitTurnGovernorFinished(characterChoosed);
    }

    public void receiveInit(int nbPlayers){

        int doublonPerPlayer = 0;
        switch (nbPlayers){
            case 4:
                doublonPerPlayer=3;
                break;
            case 5:
                doublonPerPlayer=4;
                break;
            default:
                doublonPerPlayer=2;
                break;
        }
        int nombreAleatoire = 0 + (int)(Math.random() * ((100000) + 1));
        this.player= new Player("Joueur "+nombreAleatoire,doublonPerPlayer);
        controllerPlayerLogger.log(level,"You have been initialized in the game");
        this.sendReady();

    }

    public void sendIsGameFinished(){
        this.connexionGamePlayer.emitIsGameFinished(this.getPlayer().hasFullCity());
    }

    public void receiveIsGameFinished(boolean gameFinished){
            if(gameFinished){
                canPlayTurn=false;
            }else{

                canPlayTurn=true;
                Character character = this.getPlayer().getBot().chooseCharacter(charactersList.getCharactersList());

                GameMessage.messageWhenCharacterChoosen(this.getPlayer(),character);
                getPlayer().playRole(character);
                sendTurnGovernorFinished(character);
            }
    }
    public void setConnexionGamePlayer(ConnexionGamePlayer connexionGamePlayer) {
        this.connexionGamePlayer = connexionGamePlayer;
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}
