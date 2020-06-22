package Bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puertoRico.bot.Bot;
import puertoRico.bot.BotBuilding;
import puertoRico.buildings.BuildingAction.BigMarket;
import puertoRico.buildings.BuildingAction.Building;
import puertoRico.buildings.BuildingAction.University;
import puertoRico.buildings.BuildingIndustrial.CoffeeRoaster;
import puertoRico.buildings.BuildingIndustrial.SmallIndigoPlant;
import puertoRico.buildings.BuildingIndustrial.SugarStorage;
import puertoRico.characters.Builder;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import puertoRico.type.TypeBuildingAction;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;

public class BotBuildingTest {

    private Bot bot;
    private ArrayList<Building> buildings;
    private Stock stock;

    @BeforeEach
    public void setUp(){

        buildings = new ArrayList<>();
        stock = new Stock(3);
        buildings= new ArrayList<>();
        buildings.add(new SmallIndigoPlant());
        buildings.add(new SugarStorage());
        buildings.add(new University());
        buildings.add(new CoffeeRoaster());
        buildings.add(new BigMarket());
        bot = new BotBuilding();

    }


    @Test
    /**
     * We test if the bot choose the building who have the most point of victory
     */
    public void botReturnMaxBuildingPVTest(){

        Player p = new Player("Joueur",1000);

        p.setBot(bot);
        p.playRole(new Builder(buildings,stock));
        assertEquals(true,p.getCity().get(0).getName().equals(TypeBuildingAction.UNIVERSITY.toString()));

    }


    @Test
    /**
     * We test if the bot choose the building that have the most victory points and player can pay it
     */
    public void botReturnMaxBuildingPVAndPayTest(){
        Player p = new Player("Joueur",5);
        p.setBot(bot);
        p.playRole(new Builder(buildings,stock));
        assertEquals(true,!p.getCity().get(0).getName().equals(TypeBuildingAction.UNIVERSITY.toString()));
        assertEquals(true,p.getCity().get(0).getName().equals(TypeBuildingAction.BIGMARKET.toString()));

    }



}
