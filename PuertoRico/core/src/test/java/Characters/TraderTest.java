package Characters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puertoRico.buildings.BuildingAction.Building;
import puertoRico.buildings.BuildingIndustrial.SmallIndigoPlant;
import puertoRico.characters.Builder;
import puertoRico.characters.Craftman;
import puertoRico.characters.Farmer;
import puertoRico.characters.Trader;
import puertoRico.plantations.CornPlantation;
import puertoRico.plantations.IndigoPlantation;
import puertoRico.plantations.Plantation;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import puertoRico.type.TypeWare;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TraderTest {

    private Player player;
    private Stock stockGlobal = new Stock(3);
    private int initialIndigoInStock = 11;
    private int initialCornStock=10;
    @BeforeEach
    public void setUp(){
        player= new Player("p",1);
        player.setColonist(2);
        ArrayList<Building> buildings = new ArrayList<>();
        ArrayList<Plantation> plantations = new ArrayList<>();
        SmallIndigoPlant smallIndigoPlant = new SmallIndigoPlant();
        buildings.add(smallIndigoPlant);
        IndigoPlantation indigoPlantation = new IndigoPlantation();
        plantations.add(indigoPlantation);
        Stock stock=new Stock(3);

        player.playRole(new Builder(buildings,stock));
        player.playRole(new Farmer(plantations,stock));
        player.putColonist();

    }

    /**
     Test when the player play the character "trader", the trader sell one type of ware (indigo,corn,...) and add it
     in the sellChamber stock
     */
    @Test
    public void playerWinDoublonsBySellingHisProductionTest(){
        player.playRole(new Craftman(stockGlobal));
        player.playRole(new Trader(stockGlobal,1));
        assertEquals(1,player.getInventory().getDoublons());
        assertEquals(true,stockGlobal.getStockResource(TypeWare.INDIGO)==initialIndigoInStock-1);

        ArrayList<Plantation> plantationsCorn = new ArrayList<>();
        plantationsCorn.add(new CornPlantation());
        player.playRole(new Trader(stockGlobal,1));
        player.playRole(new Farmer(plantationsCorn,stockGlobal));

        assertEquals(true,player.getInventory().getDoublons()==1);
        assertEquals(true,stockGlobal.getStockResource(TypeWare.CORN)==initialCornStock);



    }


    /**
      Test that the player don't gain any doublon when he haven't any production
     */

    @Test
     public  void  playerWithoutProduction (){
         player.playRole(new Trader(stockGlobal,1));
         assertEquals(true,player.getInventory().getDoublons()==0);

    }

    /**
      Test that the Stock didn't change when the player haven't any production
     */

    @Test
     public  void checkStockWithoutProduction () {

        player.playRole(new Trader(stockGlobal,1));
        assertEquals(true,stockGlobal.getStockResource(TypeWare.INDIGO)==initialIndigoInStock);

    }

}
