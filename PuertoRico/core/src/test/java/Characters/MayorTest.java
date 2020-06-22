package Characters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puertoRico.buildings.BuildingAction.SmallMarket;
import puertoRico.buildings.BuildingIndustrial.CoffeeRoaster;
import puertoRico.buildings.BuildingIndustrial.SmallIndigoPlant;
import puertoRico.buildings.BuildingIndustrial.TobaccoStorage;
import puertoRico.characters.Mayor;
import puertoRico.player.Player;
import puertoRico.stock.Stock;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class MayorTest {

    ArrayList<Player> playerArrayList;
    @BeforeEach
    public void setUp(){
        playerArrayList= new ArrayList<>();
        playerArrayList.add(new Player(("p1"),10));
        playerArrayList.add(new Player(("p2"),10));
        playerArrayList.add(new Player(("p3"),10));
    }


    /**
     * Verify that all players take colonist from boat
     */
    @Test
    public void allPlayersTakeColonistFromBoat(){

        Stock stock= new Stock(3);
        Mayor mayor = new Mayor(3,stock);
        for(Player p: playerArrayList){
            p.playRole(mayor);
            assertEquals(1,p.getColonist());
        }

    }

    /**
     * Verify that players take all colonist from boat
     */
    @Test
    public void playersTakeAllColonistInBoat(){

        Stock stock= new Stock(3);
        Mayor mayor = new Mayor(3,stock);
        mayor.setColonistShip(7);
        for(Player p: playerArrayList){
            p.playRole(mayor);
        }

       assertEquals(true,playerArrayList.get(0).getColonist()==3);
        assertEquals(true,playerArrayList.get(1).getColonist()==2);
        assertEquals(true,playerArrayList.get(2).getColonist()==2);

    }

    /**
     * Verify correct number of colonist in boat when the turn is finished ( players have no active buildings)
     */
    @Test
    public void numberColonistInBoat(){

        Stock stock= new Stock(3);
        Mayor mayor = new Mayor(3,stock);
        for(Player p: playerArrayList){
            p.playRole(mayor);
        }

        assertEquals(3,mayor.getColonistShip());
    }

    /**
     * Verify correct number of colonist in boat when the turn is finished ( players have active buildings)
     */
    @Test
    public void numberColonistInBoatActiveBuildings(){

        Stock stock= new Stock(3);
        Mayor mayor = new Mayor(3,stock);

        for(Player p: playerArrayList){
            p.getCity().add(new SmallMarket());
            p.getCity().add(new SmallIndigoPlant());
            p.getCity().add(new TobaccoStorage());
            p.getCity().add(new CoffeeRoaster());
            p.playRole(mayor);
        }

        // Each player put 1 colonist on one of his building, so there is 3 inactive buildings

        assertEquals(9,mayor.getColonistShip());
    }
}
