import gameServer.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puertoRico.buildings.BuildingAction.*;
import puertoRico.buildings.BuildingIndustrial.*;
import puertoRico.characters.Builder;
import puertoRico.characters.Character;
import puertoRico.plantations.CoffeePlantation;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import puertoRico.type.TypeBuildingAction;
import gameServer.ControllerStats;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class PlayerTest {

    ControllerStats controllerStats;
    @BeforeEach
    public void setUp(){
        controllerStats = mock(ControllerStats.class);
    }
    /***
     * Here we test if a building is created on the player when we create one
     */
    @Test
    public void getIndustrialBuildingsTest()
    {
        Player p =new Player("joueur test",10);
        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(new SmallMarket());
        Stock stock=new Stock(3);
        Builder b = new Builder(buildings,stock);
        ArrayList<Building> buildings2 = new ArrayList<>();
        SmallIndigoPlant indigo = new SmallIndigoPlant();
        buildings2.add(indigo);
        Builder b2 = new Builder(buildings2,stock);

        p.playRole(b);
        assertEquals(1,p.getCity().size());
        assertEquals(0,p.getIndustrialBuildings().size());
        assertFalse(p.getIndustrialBuildings().contains(indigo));

        p.playRole(b2);
        assertEquals(2,p.getCity().size());
        assertEquals(1,p.getIndustrialBuildings().size());
        assertTrue(p.getIndustrialBuildings().contains(indigo));
    }

    /***
     * test of adding money (doublons) to the player
     */
    @Test
    public void addMoneyTest()
    {
        Player p=new Player("joueur test",0);
        assertEquals(0,p.getInventory().getDoublons());
        p.getInventory().addDoublon(1);
        assertEquals(1,p.getInventory().getDoublons());
    }

    /***
     * test of removing money (doublons) to the player
     */
    @Test
    public void remMoneyTest()
    {
        Player p=new Player("joueur test",1);
        assertEquals(1,p.getInventory().getDoublons());
        p.getInventory().removeDoublon(1);
        assertEquals(0,p.getInventory().getDoublons());
    }

    /***
     * test of adding victoryPoints to the player
     */
    @Test
    public void addvictoryPointsTest()
    {
        Player p=new Player("joueur test",0);
        assertEquals(0,p.getInventory().getVictoryPoints());
        p.getInventory().addVictoryPoints(1);
        assertEquals(1,p.getInventory().getVictoryPoints());
    }

    /***
     * test of removing victoryPoints to the player
     */
    @Test
    public void remvictoryPointsTest()
    {
        Player p=new Player("joueur test",0);
        assertEquals(0,p.getInventory().getVictoryPoints());
        p.getInventory().addVictoryPoints(1);
        assertEquals(1,p.getInventory().getVictoryPoints());
        p.getInventory().removeVictoryPoints(1);
        assertEquals(0,p.getInventory().getVictoryPoints());
    }

    /***
     * if the player have 12 buildings then the function should return true
     */
    @Test
    public void hasFullCityTest()
    {
        Player p=new Player("joueur test",500);
        assertFalse(p.hasFullCity());
        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(new SmallMarket());
        buildings.add(new SmallIndigoPlant());
        buildings.add(new TobaccoStorage());
        buildings.add(new University());
        buildings.add(new CoffeeRoaster());
        buildings.add(new Office());
        buildings.add(new BigMarket());
        Stock stock=new Stock(3);
        Builder b = new Builder(buildings,stock);
        while(p.getCity().size()<11){
                p.playRole(b);
        }

        assertFalse(p.hasFullCity());

        while(p.getCity().size()<12){
            p.playRole(b);
        }
        assertEquals(12,p.getCity().size());
        assertTrue(p.hasFullCity());
    }

    /**
     * This test grant that players can't use 2 times the same character in the same turn
     */
    @Test
    public void playerChooseCharacterOnceTest(){

        int nbPlayers=3;
        Game game = new Game();
        game.setControllerStats(controllerStats);
        boolean checkPlayerUseTwiceSameCharacter = false;
        for (int i=0;i<nbPlayers;i++){
            game.playSmallTurn();
        }

        for(int i=0;i<game.getUsedCharacters().size()-1;i++){
            if(game.getUsedCharacters().get(i).getName().equals(game.getUsedCharacters().get(i+1).getName())){
                checkPlayerUseTwiceSameCharacter=true;
            }
        }
         assertEquals(true,checkPlayerUseTwiceSameCharacter==false);


    }

    /**
     * Here we test if player gain doublon when a character is choosen but was not choosen in the last turn
     */
    @Test
    public void charactersDoublonsTest(){
        Game game = new Game();
        game.setControllerStats(controllerStats);
        Player p = game.getPlayers().get(0);
        game.playTurn();
        int previousDoublon = p.getInventory().getDoublons();
        int bonus=0;
        Character characterDoublon = null ;
        for(Character character: game.getCharacterList()){
            if(character.getBonus()>0){
                characterDoublon=character;
                bonus = character.getBonus();
            }
        }

        p.playRole(characterDoublon);

        assertEquals(true,p.getInventory().getDoublons()==previousDoublon+bonus);


    }

    /**
     * Test to see if the colonist are well put in unnocupied buildings
     */
    @Test
    public void putColonistTest()
    {
        Player p=new Player("joueur test",3);
        p.setColonist(2);
        p.getFarms().add(new CoffeePlantation());
        assertFalse(p.getFarms().get(0).isOccupied());
        p.putColonist();
        assertTrue(p.getFarms().get(0).isOccupied());
        assertEquals(1,p.getColonist());
        CoffeeRoaster c=new CoffeeRoaster();
        c.putColonist();
        p.getCity().add(c);
        p.putColonist();
        assertTrue(p.getCity().get(0).isOccupied());
        assertEquals(1,p.getColonist());
    }

    /**
     * Test the recuperation of the numer of unnocupied buildings
     */
    @Test
    public void getNbUnactiveBuildingsTest()
    {
        Player p=new Player("joueur test",3);
        p.setColonist(1);
        p.getCity().add(new SmallMarket());
        p.getCity().add(new BigMarket());
        assertTrue(p.getNbUnactiveBuildings()==2);
        p.putColonist();
        assertTrue(p.getNbUnactiveBuildings()==1);
    }

    /**
     * Test the recuperation of active buildings
     */
    @Test
    public void activeBuildingTest()
    {
        Player p=new Player("joueur test",3);
        assertFalse(p.activeBuilding(TypeBuildingAction.SMALLMARKET));
        p.setColonist(1);
        p.getCity().add(new SmallMarket());
        assertFalse(p.activeBuilding(TypeBuildingAction.SMALLMARKET));
        p.putColonist();
        assertTrue(p.activeBuilding(TypeBuildingAction.SMALLMARKET));
    }

    /**
     * Test the size of the palyer city
     */
    @Test
    public void getCitySizeTest(){
        Player p=new Player("joueur test",3);
        p.getCity().add(new SmallMarket());
        assertEquals(1,p.getCitySize());
        p.getCity().add(new BigMarket());
        assertEquals(2,p.getCitySize());
    }


}
