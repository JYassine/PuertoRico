package Buildings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puertoRico.buildings.BuildingAction.Building;
import puertoRico.buildings.BuildingAction.University;
import puertoRico.buildings.BuildingIndustrial.SmallIndigoPlant;
import puertoRico.characters.Builder;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class UniversityTest {

    Builder builder;
    ArrayList<Building> buildingArrayList;
    Stock stock;
    Player p  ;


    @BeforeEach
    public void setUp(){
        p = new Player("p",20);
        buildingArrayList = new ArrayList<>();
        buildingArrayList.add(new SmallIndigoPlant());
        University university = new University();
        university.putColonist();
        p.getCity().add(university);
        stock = new Stock(3);
        builder = new Builder(buildingArrayList,stock);
    }


    @Test
    public void universityTest(){
        int oldColonistPlayer = p.getColonist();
        int oldColonistStock = stock.getColonists();
        p.playRole(builder);

        assertEquals(oldColonistStock-1,stock.getColonists());
        assertEquals(true,p.getNbUnactiveBuildings()==0);

    }
}
