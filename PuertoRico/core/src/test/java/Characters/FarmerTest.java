package Characters;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import puertoRico.characters.Farmer;
import puertoRico.plantations.IndigoPlantation;
import puertoRico.plantations.Plantation;
import puertoRico.player.Player;
import puertoRico.stock.Stock;

import java.util.ArrayList;

public class FarmerTest {


    /**
     * Here we test the method "activate" of the Farmer and see if the Farmer do the correct work
     */
    @Test
    public  void activateTest() {


        Player player = new Player("p",0);
        ArrayList<Plantation> plantations = new ArrayList<>();
        IndigoPlantation indigoPlantation = new IndigoPlantation();
        plantations.add(indigoPlantation);
        Stock stock=new Stock(3);
        player.playRole(new Farmer(plantations,stock));


        assertEquals(true, player.getFarms().contains(indigoPlantation));
        assertEquals(1, player.getFarms().size());
    }

    /**
     * Here we test if the Builder don't plant 13 plantations (maximum is 12)
     */
    @Test
    public void activate13TimesTest()
    {
        Player player = new Player("p",0);
        ArrayList<Plantation> plantations = new ArrayList<>();
        IndigoPlantation indigoPlantation = new IndigoPlantation();
        plantations.add(indigoPlantation);
        Stock stock=new Stock(3);


        for(int i =0;i<12;i++)
        {
            player.playRole(new Farmer(plantations,stock));
        }

        assertEquals(true, player.getFarms().contains(indigoPlantation));
        assertEquals(12, player.getFarms().size());

        player.playRole(new Farmer(plantations,stock));
        assertFalse(player.getFarms().size()==13);
        assertEquals(12, player.getFarms().size());

    }




}
