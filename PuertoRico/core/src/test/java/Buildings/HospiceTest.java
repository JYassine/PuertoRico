package Buildings;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puertoRico.buildings.BuildingAction.Hospice;
import puertoRico.characters.Farmer;
import puertoRico.plantations.IndigoPlantation;
import puertoRico.plantations.Plantation;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class HospiceTest {

    Farmer farmer;
    ArrayList<Plantation> plantarray;
    Stock stock;
    Player p;


    @BeforeEach
    public void setUp() {
        p = new Player("p", 20);
        plantarray = new ArrayList<>();
        plantarray.add(new IndigoPlantation());
        Hospice hospice = new Hospice();
        hospice.putColonist();
        p.getCity().add(hospice);
        stock = new Stock(3);
        farmer = new Farmer(plantarray, stock);
    }


    @Test
    public void hospiceTest() {
        p.setColonist(2);
        int oldColonistPlayer = p.getColonist();
        int oldColonistStock = stock.getColonists();
        p.playRole(farmer);

        assertEquals(oldColonistStock - 1, stock.getColonists());
        assertEquals(oldColonistPlayer, p.getColonist());
        assertEquals(true, p.getNbUnactiveBuildings() == 0);

    }
}