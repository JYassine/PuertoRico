package puertoRico.characters;
import useful.GameMessage;
import puertoRico.plantations.Plantation;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import puertoRico.type.TypeBuildingAction;

import java.util.ArrayList;


public class Farmer extends Character {

    private ArrayList<Plantation> plantations;

    public Farmer(){

    }
    public Farmer(ArrayList<Plantation> plantations, Stock stock) {
        super("Farmer", stock);
        this.plantations=plantations;
    }


    @Override
    public void activate(Player p) {
        try{
            giveBonus(p);
            if(p.getFarms().size()<12){
                Plantation plantation = p.getBot().choosePlantation(plantations);
                p.getFarms().add(plantation.clone());
                GameMessage.messageWhenPlant(p, plantation);
                if(p.activeBuilding(TypeBuildingAction.HOSPICE))
                    putColonist(p,plantation);
            }
        }catch (CloneNotSupportedException e){}
    }

    public boolean putColonist(Player p, Plantation plantation){
        if(!plantation.isOccupied()&&this.getStock().getColonists()>0){
            this.getStock().removeColonist(1);
            plantation.putColonist();
            if(plantation.isOccupied())
                GameMessage.messageWhenHospiceUsed(p,plantation);
            return plantation.isOccupied();
        }
        return false;
    }

    public ArrayList<Plantation> getPlantations() {
        return plantations;
    }

    public void setPlantations(ArrayList<Plantation> plantations) {
        this.plantations = plantations;
    }


}
