package puertoRico.bot;
import puertoRico.buildings.BuildingAction.Building;
import puertoRico.player.Player;
import java.util.ArrayList;
import java.util.Collections;

public class BotBuilding extends Bot {
    private Player player;

    public BotBuilding(){
        this.name="botBuilding";
    }
    @Override
    public Building chooseBuilding(ArrayList<Building> buildings) {

        Collections.sort(buildings,new MaxPointVictoryBuildingComparator());
        for(Building building : buildings){
            if(player.pay(building.getPrice())){
                return building;
            }
        }

        return buildings.get(random.nextInt(buildings.size()));

    }


    @Override
    public Player getPlayer(Player p) {
        return p;
    }

    @Override
    public void setPlayer(Player p) {
        this.player=p;
    }
}
