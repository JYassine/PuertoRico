package puertoRico.characters;

import useful.GameMessage;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import puertoRico.type.TypeWare;

import java.util.HashMap;

public class Craftman extends Character {

    public HashMap<String, Integer> getActiveBuildings() {
        return activeBuildings;
    }

    public void setActiveBuildings(HashMap<String, Integer> activeBuildings) {
        this.activeBuildings = activeBuildings;
    }

    public HashMap<String, Integer> getActivePlants() {
        return activePlants;
    }

    public void setActivePlants(HashMap<String, Integer> activePlants) {
        this.activePlants = activePlants;
    }

    private HashMap<String ,Integer>activeBuildings;
    private HashMap<String,Integer> activePlants;

    public Craftman(){

    }
    public Craftman(Stock stock)
    {
        super("Craftman",stock);

        initActivePlants();
        initActiveBuildings();
    }

    private void initActiveBuildings(){
        activeBuildings = new HashMap<>();
        for(TypeWare typeWare : TypeWare.values()){
            if(typeWare.requireBuilding()){
                activeBuildings.put(typeWare.toString(),0);
            }
        }
    }

    private void initActivePlants(){
        activePlants = new HashMap<>();
        for(TypeWare typeWare : TypeWare.values()){
            activePlants.put(typeWare.toString(),0);
        }
    }
    @Override
    public void activate(Player p)
    {
        giveBonus(p);
        TypeWare t;
        for (int i = 0; i < p.getIndustrialBuildings().size(); i++) {
            t=p.getIndustrialBuildings().get(i).getResources();
            if(p.getIndustrialBuildings().get(i).isOccupied())
                activeBuildings.put(t.toString(),(activeBuildings.get(t.toString())+1));
        }
        for (int i = 0; i < p.getFarms().size(); i++) {
            t=p.getFarms().get(i).getResources();
            if(p.getFarms().get(i).isOccupied())
                activePlants.put(t.toString(),(activePlants.get(t.toString())+1));
        }
        for (TypeWare typeWare: TypeWare.values()) {
            if(!typeWare.requireBuilding())
            {
                int maxCorn = Math.min(getStock().getStockResource(typeWare),activePlants.get(typeWare.toString()));
                if(maxCorn>0)
                {
                    int nbBarrel = getStock().removeFromStock(typeWare,maxCorn);
                    p.getInventory().addInStock(typeWare, nbBarrel);
                    GameMessage.messageWhenProduct(p,nbBarrel, typeWare);
                }
            }
            else{
                int maxResources = Math.min(getStock().getStockResource(typeWare),Math.min(activeBuildings.get(typeWare.toString()),activePlants.get(typeWare.toString())));
                if(maxResources>0) {
                    int nbBarrel = getStock().removeFromStock(typeWare,maxResources);
                    p.getInventory().addInStock(typeWare, nbBarrel);
                    GameMessage.messageWhenProduct(p, nbBarrel, typeWare);
                }
            }
        }
        initActiveBuildings();
        initActivePlants();
    }


}
