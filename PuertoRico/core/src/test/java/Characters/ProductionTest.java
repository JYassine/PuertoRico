package Characters;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductionTest {


    private Player player;
    private Stock stockGlobal = new Stock(3);
    int initialIndigo = 11;
    int initialCorn=10;

    @BeforeEach
    public void setUp(){
        player= new Player("p",10);
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
        player.playRole(new Craftman(stockGlobal));

    }

    /**
     * Test if the player wins ware (indigo,corn,...) when the craftman produce with industrial buildings
     */
    @Test
    public void playerWinWaresWithProductionTest()
    {

        System.out.println(player.getInventory().getStockResource(TypeWare.CORN));
        assertEquals(true,player.getInventory().getStockResource(TypeWare.INDIGO)==1);


    }

    /**
     * Test if the stock change correctly when there is an exchange between craftman and player
     */
    @Test
    public void checkStockAfterExchange(){
        assertEquals(true,stockGlobal.getStockResource(TypeWare.INDIGO)==initialIndigo-1);
    }

    /**
     * Test if corn is created even if there is no building to produce it
     */

    @Test
    public void checkCornCreatedWithoutBuilding(){
        ArrayList<Plantation> plantationsCorns = new ArrayList<>();
        plantationsCorns.add(new CornPlantation());
        player.playRole(new Farmer(plantationsCorns,stockGlobal));
        player.setColonist(1);
        player.putColonist();
        player.playRole(new Craftman(stockGlobal));
        assertEquals(initialCorn-1,stockGlobal.getStockResource(TypeWare.CORN));
    }



}
