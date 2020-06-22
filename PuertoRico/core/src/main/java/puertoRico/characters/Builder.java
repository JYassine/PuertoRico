package puertoRico.characters;
import useful.GameMessage;
import puertoRico.buildings.BuildingAction.Building;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import puertoRico.type.TypeBuildingAction;

import java.util.ArrayList;

public class Builder extends Character {

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    private ArrayList<Building> buildings;

    public Builder(){

    }
    public Builder(ArrayList<Building> buildings, Stock stock) {
        super("Builder",stock);
        this.buildings=buildings;
    }

    @Override
    public void activate(Player p) {
        try {
            giveBonus(p);
            if (p.getCity().size() < 12 && (!buildings.isEmpty())) {
                    Building building = p.getBot().chooseBuilding(buildings);
                    if(building.getNbAvailable()>0 && (!p.hasBuilding(building))) {
                        if (p.pay(building.getPrice())) {
                            if (p.getCitySize() + building.getSpaceBuilding() <= 12) {
                                Building b = building.clone();
                                p.getCity().add(b);
                                GameMessage.messageWhenConstruct(p, building);
                                p.getInventory().removeDoublon(building.getPrice());
                                getStock().addDoublon(building.getPrice());
                                p.getInventory().addVictoryPoints(building.getVictoryPoints());
                                building.remove();
                                if(building.getNbAvailable()==0)
                                    buildings.remove(building);
                                if (p.activeBuilding(TypeBuildingAction.UNIVERSITY))
                                    putColonist(p, b);
                            }
                        }
                    }
            }
        }catch (CloneNotSupportedException e){}
    }

    public boolean putColonist(Player p,Building b){
        if(!b.isOccupied()&&this.getStock().getColonists()>0){
            this.getStock().removeColonist(1);
            b.putColonist();
            if(b.isOccupied())
                GameMessage.messageWhenUniversityUsed(p,b);
            return b.isOccupied();
        }
        return false;
    }

}
