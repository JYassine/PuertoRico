package Buildings;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import puertoRico.buildings.BuildingAction.BigMarket;
import puertoRico.buildings.BuildingAction.Building;
import puertoRico.buildings.BuildingAction.SmallMarket;
import puertoRico.characters.Trader;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import puertoRico.type.TypeWare;

import java.util.ArrayList;

public class BigMarketTest {

    @Test
    public void bigMarketTest(){

        Player player =new Player("p",10);
        Stock stock = new Stock(3);
        ArrayList<Building> buildings = new ArrayList<>();
        BigMarket bigMarket = new BigMarket();
        buildings.add(bigMarket);
        bigMarket.putColonist();
        player.getCity().add(bigMarket);
        player.getInventory().addInStock(TypeWare.TOBACCO,1);
        player.playRole(new Trader(stock,1));
        Assert.assertEquals(16,player.getInventory().getDoublons());


    }


    @Test
    public  void  bigMarketTest2() {
        Player player =new Player("p",10);
        Stock stock = new Stock(3);
        ArrayList<Building> buildings = new ArrayList<>();
        BigMarket bigMarket = new BigMarket();
        buildings.add(bigMarket);
        bigMarket.putColonist();
        player.getCity().add(bigMarket);
        SmallMarket smallMarket = new SmallMarket();
        buildings.add(smallMarket);
        smallMarket.putColonist();
        player.getCity().add(smallMarket);
        player.getInventory().addInStock(TypeWare.TOBACCO,1);
        player.playRole(new Trader(stock,1));
        Assert.assertEquals(17,player.getInventory().getDoublons());
    }

}
