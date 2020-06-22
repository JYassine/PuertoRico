import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puertoRico.characters.Craftman;
import puertoRico.characters.Farmer;
import puertoRico.characters.Trader;
import puertoRico.plantations.CornPlantation;
import puertoRico.plantations.Plantation;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import puertoRico.type.TypeWare;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class SellChamberTest {

    private Player player;
    @BeforeEach
    public void setUp(){

        player = new Player("Player",10);


    }

    /**
     * We test if player can add a ware if the sell chamber is empty
     */
    @Test
    public void sellChamberEmptyTest(){
        Stock stockGlobal = new Stock(1);
        ArrayList<Plantation> plantations = new ArrayList<Plantation>();

        Plantation plantation = new CornPlantation();
        plantations.add(plantation);

        Farmer farmer = new Farmer(plantations,stockGlobal);
        player.playRole(farmer);

        player.setColonist(2);
        player.putColonist();

        player.playRole(new Craftman(stockGlobal));
        Trader trader = new Trader(stockGlobal,1);
        player.playRole(trader);

        assertEquals(true,trader.getSellChamber().getStockResource(TypeWare.CORN)==1);

    }


    /**
     * We test if player can't add the same ware in the sell chamber
     */
    @Test
    public void sellChamberSameWareTest(){
        Stock stockGlobal = new Stock(1);
        ArrayList<Plantation> plantations = new ArrayList<Plantation>();


        Plantation plantation = new CornPlantation();
        plantations.add(plantation);

        Farmer farmer = new Farmer(plantations,stockGlobal);
        player.playRole(farmer);

        player.setColonist(2);
        player.putColonist();

        player.playRole(new Craftman(stockGlobal));
        Trader trader = new Trader(stockGlobal,1);
        trader.getSellChamber().addInStock(TypeWare.CORN,1);
        player.playRole(trader);

        assertEquals(true,trader.getSellChamber().getStockResource(TypeWare.CORN)==1);
    }
}
