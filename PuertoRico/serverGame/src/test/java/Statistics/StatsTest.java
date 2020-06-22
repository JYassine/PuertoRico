package Statistics;

import gameServer.Game;
import gameServer.ControllerStats;
import stats.InfoPlayer;
import stats.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import puertoRico.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class StatsTest {

    ControllerStats controllerStats;
    int nbMove=0;
    Statistics statistics;
    ArrayList<InfoPlayer> infoPlayers;
    InfoPlayer infoPlayer;
    InfoPlayer infoPlayer2;
    InfoPlayer infoPlayer3;

    @BeforeEach
    public void setUp(){
        controllerStats = mock(ControllerStats.class);
        statistics = new Statistics();
        statistics.setNumberGame(1);
        infoPlayers = new ArrayList<>();
        infoPlayer = new InfoPlayer();
        infoPlayer2= new InfoPlayer();
        infoPlayer3= new InfoPlayer();
        infoPlayers.add(infoPlayer);
        infoPlayers.add(infoPlayer2);
        infoPlayers.add(infoPlayer3);


    }

    /**
     * Chaque fois que le joueur joue un personnage, cela est compté comme un coup de plus dans la partie
     */

    @Test
    public void nbMoveTest(){
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                statistics.calculateNbMoveInGame(1);
                return null;
            }
        }).when(controllerStats).sendNbMove();

        Game game = new Game();
        game.setControllerStats(controllerStats);
        game.playSmallTurn();

        assertEquals(game.getPlayers().size(),statistics.getNbMoveInGame());

    }

    /**
     * Calculer le nombre de victoire de chaque joueur en fin de partie
     */

    @Test
    public void calculNbWinForPlayers(){

        Game game = new Game();
        game.setControllerStats(controllerStats);
        game.setActiveConnexion(true);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                for(int i=0;i<infoPlayers.size();i++){
                    infoPlayers.get(i).setNamePlayer(game.getPlayers().get(i).getName());
                    infoPlayers.get(i).setGameEven(false);
                    infoPlayers.get(i).setStrategyPlayer(game.getPlayers().get(i).getBot().getName());
                    infoPlayers.get(i).setWinner(game.getPlayers().get(i).isWinner());
                    infoPlayers.get(i).setVictoryPoint(game.getPlayers().get(i).getInventory().getVictoryPoints());
                }
                for(InfoPlayer infoPlayer: infoPlayers){
                    statistics.save(0,infoPlayer);
                }


                return null;
            }
        }).when(controllerStats).sendGameFinished();

        game.startGame();

        for(InfoPlayer infoPlayer : infoPlayers){
            if(infoPlayer.isWinner()){
                assertEquals(true,statistics.calculateNumberWin(infoPlayer.getNamePlayer())==1);
            }else{
                assertEquals(true,statistics.calculateNumberWin(infoPlayer.getNamePlayer())==0);
            }
        }




    }

    /**
     * Calculer le nombre de partie perdues de chaque joueur en fin de partie
     */

    @Test
    public void calculNumberLooseForPlayers(){

        Game game = new Game();
        game.setControllerStats(controllerStats);

        game.setActiveConnexion(true);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                for(int i=0;i<infoPlayers.size();i++){
                    infoPlayers.get(i).setNamePlayer(game.getPlayers().get(i).getName());
                    infoPlayers.get(i).setGameEven(false);
                    infoPlayers.get(i).setStrategyPlayer(game.getPlayers().get(i).getBot().getName());
                    infoPlayers.get(i).setWinner(game.getPlayers().get(i).isWinner());
                    infoPlayers.get(i).setVictoryPoint(game.getPlayers().get(i).getInventory().getVictoryPoints());
                }
                for(InfoPlayer infoPlayer: infoPlayers){
                    statistics.save(0,infoPlayer);
                }


                return null;
            }
        }).when(controllerStats).sendGameFinished();

        game.startGame();

        for(InfoPlayer infoPlayer : infoPlayers){
            if(!infoPlayer.isWinner()){
                assertEquals(true,statistics.calculateNumberLose(infoPlayer.getNamePlayer())==1);
            }else{
                assertEquals(true,statistics.calculateNumberLose(infoPlayer.getNamePlayer())==0);
            }
        }




    }

    /**
     * Calculer le nombre de partie nul de chaque joueur en fin de partie
     */

    @Test
    public void calculateNumberEvenForPlayers(){

        Game game = new Game();
        game.setControllerStats(controllerStats);

        game.setActiveConnexion(true);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                for(int i=0;i<infoPlayers.size();i++){
                    infoPlayers.get(i).setNamePlayer(game.getPlayers().get(i).getName());
                    infoPlayers.get(i).setGameEven(true);
                    infoPlayers.get(i).setStrategyPlayer(game.getPlayers().get(i).getBot().getName());
                    infoPlayers.get(i).setWinner(game.getPlayers().get(i).isWinner());
                    infoPlayers.get(i).setVictoryPoint(game.getPlayers().get(i).getInventory().getVictoryPoints());
                }
                for(InfoPlayer infoPlayer: infoPlayers){
                    statistics.save(0,infoPlayer);
                }


                return null;
            }
        }).when(controllerStats).sendGameFinished();

        game.startGame();

        for(InfoPlayer infoPlayer : infoPlayers){
            if(infoPlayer.isGameEven()){
                assertEquals(true,statistics.calculateNumberGameEven(infoPlayer.getNamePlayer())==1);
            }else{
                assertEquals(true,statistics.calculateNumberGameEven(infoPlayer.getNamePlayer())==0);
            }
        }

    }

    /**
     * Calculer le nombre de point de victoires de chaque joueur en fin de partie
     */

    @Test
    public void calculatePointVictory(){

        Game game = new Game();
        game.setControllerStats(controllerStats);

        game.setActiveConnexion(true);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                for(int i=0;i<infoPlayers.size();i++){
                    infoPlayers.get(i).setNamePlayer(game.getPlayers().get(i).getName());
                    infoPlayers.get(i).setGameEven(game.getPlayers().get(i).isGameEven());
                    infoPlayers.get(i).setStrategyPlayer(game.getPlayers().get(i).getBot().getName());
                    infoPlayers.get(i).setWinner(game.getPlayers().get(i).isWinner());
                    infoPlayers.get(i).setVictoryPoint(game.getPlayers().get(i).getInventory().getVictoryPoints());
                }
                for(InfoPlayer infoPlayer: infoPlayers){
                    statistics.save(0,infoPlayer);
                }


                return null;
            }
        }).when(controllerStats).sendGameFinished();

        HashMap<String,Integer> totalPointVictory = new HashMap<>();
        for(Player p : game.getPlayers()){
            totalPointVictory.put(p.getName(),0);
        }

        game.startGame();
        for(Player p : game.getPlayers()){
            totalPointVictory.put(p.getName(),p.getInventory().getVictoryPoints()+totalPointVictory.get(p.getName()));
        }

        for(InfoPlayer infoPlayer : infoPlayers){
            assertEquals(statistics.calculatePointVictory(infoPlayer.getNamePlayer()),
                    (int)totalPointVictory.get(infoPlayer.getNamePlayer()));
        }




    }


}
