package Buildings;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import puertoRico.buildings.BuildingAction.Building;
import puertoRico.buildings.BuildingIndustrial.SmallIndigoPlant;
import puertoRico.characters.Builder;
import puertoRico.player.Player;
import puertoRico.stock.Stock;

import java.util.ArrayList;


public class BuildingTest {


    @Test
     public void buildingCreatedTest()
    {

        Player player = new Player("p",10);
        ArrayList<Building> buildings = new ArrayList<>();
        Stock stock=new Stock(3);
        // We choose a random building here...
        SmallIndigoPlant smallIndigoPlant =new SmallIndigoPlant();
        buildings.add(smallIndigoPlant);
        player.playRole(new Builder(buildings,stock));

        assertEquals(true,player.getCity().contains(smallIndigoPlant));


    }




}
