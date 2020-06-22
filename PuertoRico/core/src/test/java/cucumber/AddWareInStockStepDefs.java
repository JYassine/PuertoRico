package cucumber;
import io.cucumber.java8.En;
import puertoRico.buildings.BuildingAction.Building;
import puertoRico.characters.Trader;
import static org.junit.jupiter.api.Assertions.*;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import puertoRico.type.TypeWare;
import java.lang.reflect.Constructor;


public class AddWareInStockStepDefs implements En {

    Player p;
    Stock stock;
    Trader trader ;
    private int oldDoublons;

    public AddWareInStockStepDefs(){

        Given("a player of name {string} with the character trader and the stock of the game",(String namePlayer) -> {

            p = new Player(namePlayer,10);
            stock = new Stock(3);
            trader = new Trader(stock,1);
        });

        And("a building {string}",(String nameBuilding) -> {

            Constructor constructorBuilding = Class.forName("puertoRico.buildings.BuildingIndustrial."+nameBuilding).getConstructor();
            Building building = (Building) constructorBuilding.newInstance();
            p.getCity().add(building);

        });


        When("{string} have {int} {string}",(String playerName, Integer nb,String nameTypeWare) -> {

            TypeWare resources = TypeWare.valueOf(nameTypeWare);
            p.getInventory().addInStock(resources,nb);

        });


        And("{string} choose to play the trader",(String namePlayer) -> {

            oldDoublons = p.getInventory().getDoublons();
             p.playRole(trader);

        });


        Then("{string} gain {int} doublon",(String name, Integer doublons) -> {
            assertEquals(true,p.getInventory().getDoublons()==oldDoublons+doublons);

        });
        And("{int} {string} is added in sell chamber stock",(Integer nb,String nameTypeWare) -> {
            int initialValueInStock=0;
            TypeWare resources = TypeWare.valueOf(nameTypeWare);
            assertEquals(true,trader.getSellChamber().getStockResource(resources)==initialValueInStock+nb);
        });








    }

}
