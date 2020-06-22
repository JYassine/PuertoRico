
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puertoRico.buildings.BuildingAction.Building;
import puertoRico.buildings.BuildingIndustrial.SmallIndigoPlant;
import puertoRico.characters.Builder;
import puertoRico.characters.Craftman;
import puertoRico.characters.Farmer;
import puertoRico.plantations.CornPlantation;
import puertoRico.plantations.IndigoPlantation;
import puertoRico.plantations.Plantation;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import puertoRico.type.TypeWare;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ColonistTest {

    private Player player;
    @BeforeEach
    public void setUp(){
       // System.setOut(new NullPrintStream());
        player = new Player("Player",20);
    }
    @Test
    public void colonistInPlantationTest(){
        ArrayList<Plantation> plantations = new ArrayList<Plantation>();

        Plantation plantation = new CornPlantation();
        plantations.add(plantation);
        Stock stock=new Stock(3);

        Farmer farmer = new Farmer(plantations,stock);
        player.playRole(farmer);

        player.setColonist(2);
        player.putColonist();

        player.playRole(new Craftman(stock));

        assertEquals(true,player.getInventory().getStockResource(TypeWare.CORN)==1);
    }

    @Test
    public void colonistInBuildingTest(){


        Stock stock = new Stock(3);
        ArrayList<Plantation> plantations = new ArrayList<Plantation>();

        ArrayList<Building> buildings = new ArrayList<Building>();
        buildings.add(new SmallIndigoPlant());
        player.playRole(new Builder(buildings,stock));
        Plantation plantation = new IndigoPlantation();
        plantations.add(plantation);
        Farmer farmer = new Farmer(plantations,stock);
        player.playRole(farmer);

        player.setColonist(2);
        player.putColonist();
        player.playRole(new Craftman(stock));

        assertEquals(true,player.getInventory().getStockResource(TypeWare.INDIGO)==1);

    }


    @Test
    public void noColonistInBuildingTest(){


        Stock stock = new Stock(3);
        ArrayList<Plantation> plantations = new ArrayList<Plantation>();

        ArrayList<Building> buildings = new ArrayList<Building>();
        buildings.add(new SmallIndigoPlant());
        player.playRole(new Builder(buildings,stock));
        Plantation plantation = new IndigoPlantation();
        plantations.add(plantation);
        Farmer farmer = new Farmer(plantations,stock);
        player.playRole(farmer);

        player.playRole(new Craftman(stock));

        assertEquals(true,player.getInventory().getStockResource(TypeWare.INDIGO)==0);



    }

    @Test
    public void noColonistInPlantationTest(){


        Stock stock = new Stock(3);
       ArrayList<Plantation> plantations = new ArrayList<Plantation>();

       Plantation plantation = new CornPlantation();
       plantations.add(plantation);

        Farmer farmer = new Farmer(plantations,stock);
        player.playRole(farmer);

        player.playRole(new Craftman(stock));

        assertEquals(true,player.getInventory().getStockResource(TypeWare.CORN)==0);




    }
}
