import gameServer.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import puertoRico.buildings.BuildingAction.*;
import puertoRico.buildings.BuildingIndustrial.CoffeeRoaster;
import puertoRico.buildings.BuildingIndustrial.SmallIndigoPlant;
import puertoRico.buildings.BuildingIndustrial.SugarStorage;
import puertoRico.buildings.BuildingIndustrial.TobaccoStorage;
import puertoRico.characters.Builder;
import puertoRico.player.Player;
import gameServer.ControllerStats;

import java.util.ArrayList;


public class GameTest {

    ControllerStats controllerStats;

    @BeforeEach
    public void setUp(){
        controllerStats = mock(ControllerStats.class);

    }
    /**
     * Here we test if the winner is the player who have more doublons than others players
     */
    @Test
    public void determineWinnerTest(){
        int nbTurns =3;

        Game game = new Game();

        game.setActiveConnexion(true);
        game.setControllerStats(controllerStats);
        for(int i=0;i<3;i++){
            for(Player p : game.getPlayers()){
                if(p.getName().equals("Joueur 0")){
                    p.getInventory().addDoublon(1000);
                }
            }
        }

        game.endGame();
        assertEquals(true,game.getWinner().getName().equals("Joueur0"));

    }

    /**
     * Here we test if the governor on the game is changed on each turn
     */
    @Test
     public void  gouvernorTest(){
        Game game =new Game();

        game.setActiveConnexion(true);
        game.setControllerStats(controllerStats);
        int p=game.getCurrentPlayer();
        game.playTurn();

        assertEquals(true,game.getCurrentPlayer()!=p);
    }

    /**
     * Here we test if the game is finished when one player have 12 buildings and there is no colonist in stock
     */
    @Test
    public void endTest()
    {

        Game game = new Game();

        game.setActiveConnexion(true);
        Player player = game.getPlayers().get(0);
        player.getInventory().setDoublons(1000);
        ArrayList<Building> buildings = new ArrayList<>();
        game.setControllerStats(controllerStats);
        buildings.add(new SmallIndigoPlant());
        buildings.add(new TobaccoStorage());
        buildings.add(new University());
        buildings.add(new CoffeeRoaster());
        buildings.add(new Hospice());
        buildings.add(new Office());
        buildings.add(new SugarStorage());
        buildings.add(new BigMarket());
        while(player.getCity().size()<12)
        {
            player.playRole(new Builder(buildings,game.getStock()));
        }
        game.getStock().setColonists(0);
        assertEquals(12,game.getPlayers().get(0).getCity().size());
        assertEquals(true,game.checkEnd());
    }




}
