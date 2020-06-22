package puertoRico.characters;
import useful.GameMessage;
import puertoRico.boat.Boat;
import puertoRico.boat.MaximumCapacityBoatComparator;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import puertoRico.type.TypeWare;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Captain extends Character {

    public ArrayList<Boat> getBoats() {
        return boats;
    }

    public void setBoats(ArrayList<Boat> boats) {
        this.boats = boats;
    }

    private ArrayList<Boat> boats;

    public ArrayList<TypeWare> getResources() {
        return resources;
    }

    public void setResources(ArrayList<TypeWare> resources) {
        this.resources = resources;
    }

    private ArrayList<TypeWare> resources ;

    public Captain(){

    }
    public Captain(ArrayList<Boat> boats, Stock stock){
        super("Captain",stock);
        this.boats=boats;
        resources =  new ArrayList<>(Arrays.asList(TypeWare.values()));


    }
    @Override
    public void activate(Player p) {
        giveBonus(p);
        TypeWare wareToLoadInBoat;
        Boat boatToLoad;
        ArrayList<Boat> emptyBoats = getEmptyBoats();
        Collections.sort(emptyBoats,new MaximumCapacityBoatComparator());

        if(emptyBoats.size()>1){
            boatToLoad = emptyBoats.get(0);
            wareToLoadInBoat = p.getBot().chooseTypeWare(resources);
        }else{
            boatToLoad=p.getBot().chooseBoat(boats);
            if(!boatToLoad.getStock().isEmpty()){
                wareToLoadInBoat = boatToLoad.getTypeWare();
            }else{
                wareToLoadInBoat = p.getBot().chooseTypeWare(resources);
            }
        }


        int numberWarePlayer = p.getInventory().getStockResource(wareToLoadInBoat);
        int numberPlacesInBoat = boatToLoad.getMaximumCapacity() - boatToLoad.getStock().size();

        if(numberWarePlayer>0 && numberWarePlayer > numberPlacesInBoat){
            boatToLoad.importWare(wareToLoadInBoat,numberPlacesInBoat);
            p.getInventory().removeFromStock(wareToLoadInBoat,numberPlacesInBoat);
            GameMessage.messageWhenLoadBoat(p,numberPlacesInBoat,boatToLoad.getTypeWare(),boatToLoad.getNumberBoat());
        }else if(numberWarePlayer>0 && numberWarePlayer <= numberPlacesInBoat){
            boatToLoad.importWare(wareToLoadInBoat,numberWarePlayer);
            p.getInventory().removeFromStock(wareToLoadInBoat,numberWarePlayer);
            GameMessage.messageWhenLoadBoat(p,numberWarePlayer,boatToLoad.getTypeWare(),boatToLoad.getNumberBoat());
        }

        if(boatToLoad.isFull()){
            boatToLoad.sendWare();
            int d=getStock().removeVictoryPoints(boatToLoad.getMaximumCapacity());
            p.getInventory().addVictoryPoints(d);
            GameMessage.messageWhenBoatSended(p,boatToLoad.getNumberBoat(),d);
        }
    }

    private ArrayList<Boat> getEmptyBoats()
    {
        ArrayList<Boat> boatsEmpty = new ArrayList<>();
        for(Boat b : boats){
            if(b.getStock().isEmpty()){
                boatsEmpty.add(b);
            }
        }

        return boatsEmpty;
    }


}

