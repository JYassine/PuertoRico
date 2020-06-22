package Characters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puertoRico.buildings.BuildingAction.*;
import puertoRico.buildings.BuildingIndustrial.CoffeeRoaster;
import puertoRico.buildings.BuildingIndustrial.SmallIndigoPlant;
import puertoRico.buildings.BuildingIndustrial.SugarStorage;
import puertoRico.buildings.BuildingIndustrial.TobaccoStorage;
import puertoRico.characters.Builder;
import puertoRico.player.Player;
import puertoRico.stock.Stock;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class BuilderTest {


    @BeforeEach
    public void setUp(){

    }

    /**
     * Here we test the method "activate" of the Builder and see if the Builder do the correct work
     */
    @Test
    public  void activateTest() {

        Player player = new Player("p",1);
        ArrayList<Building> buildings = new ArrayList<>();
        SmallMarket smallIMarket = new SmallMarket();
        Stock stock=new Stock(3);
        buildings.add(smallIMarket);
        player.playRole(new Builder(buildings,stock));


        assertEquals(true, player.getCity().contains(smallIMarket));
        assertEquals(1, player.getCity().size());
    }

    /**
     * Here we test if the Builder don't build 13 buildings (maximum is 12)
     */
    @Test
    public void activate13TimesTest()
    {

        Player player = new Player("p",500);
        ArrayList<Building> buildings = new ArrayList<>();
        SmallMarket smallIMarket = new SmallMarket();
        Stock stock=new Stock(3);
        buildings.add(smallIMarket);
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
            player.playRole(new Builder(buildings,stock));
        }

        assertEquals(true, player.getCity().contains(smallIMarket));
        assertEquals(12, player.getCity().size());

        player.playRole(new Builder(buildings,stock));
        assertFalse(player.getCity().size()==13);
        assertEquals(12, player.getCity().size());

    }

    @Test
    public void priceTest(){
        Player player = new Player("p",1);
        ArrayList<Building> buildings = new ArrayList<>();
        BigMarket bigMarket = new BigMarket();
        Stock stock=new Stock(3);
        buildings.add(bigMarket);
        player.playRole(new Builder(buildings,stock));

        assertFalse(player.getCity().contains(bigMarket));
        assertEquals(0, player.getCity().size());


        player.getInventory().setDoublons(5);
        player.playRole(new Builder(buildings,stock));

        assertTrue(player.getCity().contains(bigMarket));
        assertEquals(1, player.getCity().size());
    }

    @Test
    public void noDuplicatesTest(){
        Player player = new Player("p",10);
        ArrayList<Building> buildings = new ArrayList<>();
        BigMarket bigMarket = new BigMarket();
        Stock stock=new Stock(3);
        buildings.add(bigMarket);
        player.playRole(new Builder(buildings,stock));

        assertTrue(player.getCity().contains(bigMarket));
        assertEquals(1, player.getCity().size());


        player.playRole(new Builder(buildings,stock));

        assertTrue(player.getCity().contains(bigMarket));
        assertEquals(1, player.getCity().size());
    }

    @Test
    public void builingHave2CopiesTest(){
        Player player = new Player("p",5);
        Player player2 = new Player("p2",5);
        Player player3 = new Player("p3",5);
        ArrayList<Building> buildings = new ArrayList<>();
        BigMarket bigMarket = new BigMarket();
        Stock stock=new Stock(3);
        buildings.add(bigMarket);
        Builder builder=new Builder(buildings,stock);
        player.playRole(builder);

        assertTrue(player.getCity().contains(bigMarket));
        assertEquals(1, player.getCity().size());


        player2.playRole(builder);

        assertTrue(player2.getCity().contains(bigMarket));
        assertEquals(1, player2.getCity().size());

        player3.playRole(builder);
        assertFalse(player3.getCity().contains(bigMarket));
        assertEquals(0, player3.getCity().size());
    }


}


