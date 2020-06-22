package gameServer;

import action.ActionPlayer;
import com.corundumstudio.socketio.SocketIOClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.socket.client.Socket;
import puertoRico.characters.Character;
import puertoRico.characters.Characters;
import stats.InfoPlayer;
import useful.GameMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerGame {


    private GameEngine game;
    private int nbPlayers ;
    private int nbPlayersConnected = 0;
    private int numberSmallTurn = 0;
    private AtomicInteger nbPlayersFinishedSmallTurn = new AtomicInteger(0);
    private HashMap<String, SocketIOClient> players;
    private static Logger loggerControllerGame = Logger.getLogger(String.valueOf(ControllerGame.class));
    private Level level;
    private InfoPlayer winnerGame;
    private HashMap<String,InfoPlayer> playersStat ;

    private boolean playerHasFullCity = false;
    private AtomicInteger allPlayersSendGameFinished = new AtomicInteger(0);

    public ControllerGame(GameEngine game) {
        this.game = game;
        playersStat = new HashMap<>();
        this.level=Level.INFO;
        this.players = new HashMap<>();
        nbPlayers=3;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public GameEngine getGame() {
        return game;
    }

    public void setGame(GameEngine game) {
        this.game = game;
    }


    public int getNbPlayersFinishedSmallTurn() {
        return nbPlayersFinishedSmallTurn.intValue();
    }



    public int getNbPlayers() {
        return nbPlayers;
    }


    public void sendInit(SocketIOClient socketIOClient) throws JsonProcessingException {

        socketIOClient.sendEvent(ActionPlayer.INIT.getNameAction(), nbPlayers);

    }

    public void sendCurrentPlayer() throws JsonProcessingException {
        ArrayList keys = new ArrayList(players.keySet());
        String namePlayer = (String) keys.get(game.getCurrentPlayer());

        ObjectMapper mapper = new ObjectMapper();
        Characters serializedCharacters = new Characters();
        serializedCharacters.setCharactersList(game.getCharacterList());
        String jsonCharacters = mapper.writeValueAsString(serializedCharacters);
        players.get(namePlayer).sendEvent(ActionPlayer.CURRENT_PLAYER_TURN.getNameAction(), jsonCharacters);

    }

    public void receiveReady(SocketIOClient socketIOClient, String newPlayer) throws JsonProcessingException {

        nbPlayersConnected++;
        players.put(newPlayer, socketIOClient);

        if (nbPlayersConnected == nbPlayers) {

            loggerControllerGame.log(level,"All players are ready, the game will start...");
            game = new GameEngine(nbPlayers);
            game.getControllerStats().sendNumberPlayer(nbPlayers);
            game.getControllerStats().setControllerGame(this);
            GameMessage.setLevel(Level.INFO);
            sendCurrentPlayer();

        }

    }

    public void sendDisconnectToAllPlayers(){
        for (String player : players.keySet()){
            players.get(player).sendEvent(Socket.EVENT_DISCONNECT);
        }
    }

    public void receiveTurnPlayed(String characterChoosed) throws JsonProcessingException {

        if (nbPlayersFinishedSmallTurn.incrementAndGet() == getNbPlayers() && !((game.checkEnd() || playerHasFullCity))) {
            loggerControllerGame.log(level,"New turn will start");
            game.updateCurrentPlayer(nbPlayers);
            nbPlayersFinishedSmallTurn.set(0);
            numberSmallTurn=0;
            ObjectMapper mapper = new ObjectMapper();
            Character characterToUpdate = null;
            try {
                characterToUpdate = mapper.readValue(characterChoosed, Character.class);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            game.updateCharacterList(game.getCharacterList(),characterToUpdate);
            for (Character c: game.getCharacterList()) {
                c.bonusIncrement();
            }
            game.getCharacterList().addAll(game.getUsedCharacters());
            game.getUsedCharacters().clear();
            sendCurrentPlayer();
        }else{
            if((game.checkEnd() || playerHasFullCity)){
                for(String player : players.keySet()){
                    players.get(player).sendEvent(ActionPlayer.WINNER.getNameAction());
                }
            }
        }
    }

    public void receiveIsGameFinished(boolean hasFullCity) {
        if (!(allPlayersSendGameFinished.intValue()== nbPlayers)) {
            allPlayersSendGameFinished.getAndIncrement();
            playerHasFullCity = playerHasFullCity || hasFullCity;
        }
    }

    private boolean checkGameEven(InfoPlayer infoPlayer){
        boolean gameEven=false;
        for(String p : playersStat.keySet()){
            if(!infoPlayer.getNamePlayer().equals(p) && infoPlayer.getVictoryPoint()==playersStat.get(p).getVictoryPoint()){
                infoPlayer.setGameEven(true);
                playersStat.get(p).setGameEven(true);
                gameEven=true;
            }
        }
        return gameEven;
    }

    public void receiveWinner(InfoPlayer player){



        if(playersStat.size()==0){
            winnerGame =  player;
        }
        playersStat.put(player.getNamePlayer(),player);
        if(playersStat.size() == nbPlayers){
            for(String p: playersStat.keySet()){
                if( playersStat.get(p).getVictoryPoint() > winnerGame.getVictoryPoint()){
                    winnerGame = playersStat.get(p);
                }
            }
            for(String p : playersStat.keySet()){
                checkGameEven(playersStat.get(p));

                if(winnerGame.getNamePlayer().equals(p)){
                    winnerGame.setWinner(true);
                    game.getControllerStats().sendInfoPlayer(winnerGame);
                }else{
                    playersStat.get(p).setWinner(false);

                    game.getControllerStats().sendInfoPlayer(playersStat.get(p));
                }
            }

            game.getControllerStats().sendGameFinished();

        }


    }
    

    public boolean isPlayerHasFullCity() {
        return playerHasFullCity;
    }



    public void receiveTurnGovernorFinished(String characterChoosedByGovernor) {

        if (!(game.checkEnd() || playerHasFullCity)) {
            nbPlayersFinishedSmallTurn.getAndIncrement();
            game.getControllerStats().sendNbMove();
            ObjectMapper mapper = new ObjectMapper();
            ArrayList keys = new ArrayList(players.keySet());
            Character characterChoosed = null;
            try {
                characterChoosed = mapper.readValue(characterChoosedByGovernor, Character.class);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            game.updateCharacterList(game.getCharacterList(), characterChoosed);
            game.getCharacterList().remove(characterChoosed);
            game.getUsedCharacters().add(characterChoosed);


            loggerControllerGame.log(level,"Governor choose "+characterChoosed.getName());

            for (int i = 1; i < nbPlayers; i++) {
                players.get(keys.get((i + game.getCurrentPlayer()) % nbPlayers)).sendEvent(ActionPlayer.PLAY_TURN.getNameAction(), characterChoosedByGovernor);

                loggerControllerGame.log(level,keys.get((i + game.getCurrentPlayer()) % nbPlayers)+" play "+characterChoosed.getName());
            }

        }else{
            if((game.checkEnd() || playerHasFullCity)){
                for(String player : players.keySet()){
                    players.get(player).sendEvent(ActionPlayer.WINNER.getNameAction());
                }
            }
        }
    }

}
