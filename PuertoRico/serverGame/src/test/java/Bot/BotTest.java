package Bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import puertoRico.bot.BotRandom;
import puertoRico.buildings.BuildingAction.Building;
import puertoRico.buildings.BuildingIndustrial.SmallIndigoPlant;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Random;

import static org.mockito.Mockito.*;

public class BotTest {

    private BotRandom bot;
    private Random random;
    private ArrayList<Building> buildings;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        random = mock(Random.class);
        buildings = new ArrayList<>();
        bot = new BotRandom();
        bot.setRandom(random);
    }


    @Test
    /**
     * There is no building when robot want to choose one building
     */
    public void BotReturnNoBuildingTest(){

        when((random).nextInt(anyInt())).thenReturn(0);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            bot.chooseBuilding(buildings);
        } );

    }

    @Test
    /**
     * There is building when robot want to choose one building
     */
    public void BotReturnBuildingTest(){

        buildings.add(new SmallIndigoPlant());

        when((random).nextInt(anyInt())).thenReturn(0);

        Building building = bot.chooseBuilding(buildings);

        assertEquals(true,building != null);

    }

    @Test
    public void badValue(){
        when((random).nextInt(anyInt())).thenReturn(-1);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            bot.chooseBuilding(buildings);
        } );

        when((random).nextInt(anyInt())).thenReturn(buildings.size()+1);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            bot.chooseBuilding(buildings);
        } );
    }
}
