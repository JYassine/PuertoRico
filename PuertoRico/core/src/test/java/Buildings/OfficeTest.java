package Buildings;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import puertoRico.buildings.BuildingAction.BigMarket;
import puertoRico.buildings.BuildingAction.Building;
import puertoRico.buildings.BuildingAction.Office;
import puertoRico.characters.Trader;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import puertoRico.type.TypeWare;

import java.util.ArrayList;

public class OfficeTest {

    @Test
    public void officeTest(){

        Player player =new Player("p",10);
        Stock stock = new Stock(3);
        Trader trader = new Trader(stock,3);
        trader.getSellChamber().addInStock(TypeWare.TOBACCO,1);
        ArrayList<Building> buildings = new ArrayList<>();
        BigMarket bigMarket = new BigMarket();
        Office office = new Office();

        buildings.add(bigMarket);
        buildings.add(office);
        bigMarket.putColonist();
        office.putColonist();
        player.getCity().add(office);
        player.getCity().add(bigMarket);

        player.getInventory().addInStock(TypeWare.TOBACCO,1);
        player.playRole(trader);
        Assert.assertEquals(0,player.getInventory().getStockResource(TypeWare.TOBACCO));



    }
}
