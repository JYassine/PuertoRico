package Characters;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import puertoRico.boat.Boat;
import puertoRico.bot.Bot;
import puertoRico.characters.Captain;

import puertoRico.player.Player;
import puertoRico.stock.Stock;
import puertoRico.type.TypeWare;

import java.util.ArrayList;

public class CaptainTest {

    private Bot bot;
    private ArrayList<Boat> boatArrayList;
    @BeforeEach
    public void setUp(){
        bot = mock(Bot.class);
        boatArrayList= new ArrayList<>();
        Boat b1 = new Boat(1);
        b1.setMaximumCapacity(6);
        Boat b2 = new Boat(2);
        b2.setMaximumCapacity(5);
        Boat b3 = new Boat(3);
        b3.setMaximumCapacity(5);
        boatArrayList.add(b1);
        boatArrayList.add(b2);
        boatArrayList.add(b3);
    }
    /**
     * Verify that a player choose the ware that exist in boat
     */
    @Test
    public void PlayerChooseWareExistBoat(){
        for(Boat b : boatArrayList){
            b.getStock().clear();

        }
        for(Boat b : boatArrayList){
            b.importWare(TypeWare.INDIGO,1);
        }
        Player p = new Player("p",10);
        int typeWare=3;
        p.getInventory().addInStock(TypeWare.INDIGO,typeWare);
        Stock stock=new Stock(3);

        p.playRole(new Captain(boatArrayList,stock));

        assertEquals(true,p.getInventory().getStockResource(TypeWare.INDIGO)==0);


    }

    /**
     * Verify that the player that send the boat gain 1 doublon
     */
    @Test
    public void playerSendGainDoublon(){
        for(Boat b : boatArrayList){
            b.getStock().clear();
        }
        Player p = new Player("p",10);
        int typeWare = 6;
        p.setBot(bot);
        when((bot).chooseTypeWare(any())).thenReturn(TypeWare.INDIGO);
        p.getInventory().addInStock(TypeWare.INDIGO, typeWare);
        Stock stock=new Stock(3);
        int oldPointVictory = p.getInventory().getVictoryPoints();
        p.playRole(new Captain(boatArrayList,stock));
        assertEquals(true, p.getInventory().getVictoryPoints() == oldPointVictory+boatArrayList.get(0).getMaximumCapacity());

    }

    /**
     * Verify that player choose the biggest boat if there exist at least 2 emptys
     */
    @Test
    public void playerChooseBiggestBoat(){
        for(Boat b : boatArrayList){
            b.getStock().clear();
        }

        Player p = new Player("p",10);
        int typeWarePlayer=5;
        p.getInventory().addInStock(TypeWare.INDIGO,typeWarePlayer);
        Stock stock=new Stock(3);
        p.playRole(new Captain(boatArrayList,stock));


        for(Boat boat : boatArrayList){
            if(boat.getStock().size()==typeWarePlayer){
                Assert.assertEquals(true,boat.getMaximumCapacity()==6);
            }
        }


    }


}
