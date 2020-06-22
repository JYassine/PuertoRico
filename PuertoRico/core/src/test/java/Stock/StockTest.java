package Stock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puertoRico.characters.Craftman;
import puertoRico.plantations.CornPlantation;
import puertoRico.plantations.Plantation;
import puertoRico.player.Player;
import puertoRico.type.TypeWare;
import puertoRico.stock.Stock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

public class StockTest {

    Stock stockGlobal;
    Player player1;
    ArrayList<Plantation> plantations;
    @BeforeEach
    public void setUp(){

        player1  = new Player("Player1",65);
        stockGlobal = new Stock(3);
        plantations = new ArrayList<>();
        for (int i=0;i < 12;i++){
            plantations.add(new CornPlantation());
        }
    }

    /**
     * Test sur le stock du jeu, vérifier que le stock n'a pas de ressources illimités
     */
    @Test
    public void testStockWareLimited(){

        int maxCorn=7;

        int maximumCornStock = stockGlobal.getStockResource(TypeWare.CORN);
        player1.setFarms(plantations);
        player1.setColonist(12);
        player1.putColonist();
        player1.playRole(new Craftman(stockGlobal));
        assertEquals(true,stockGlobal.getStockResource(TypeWare.CORN)==0);
        assertEquals(maximumCornStock,player1.getInventory().getStockResource(TypeWare.CORN));


    }



}
