package puertoRico.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.json.JSONException;
import org.json.JSONObject;
import puertoRico.bot.Bot;
import puertoRico.characters.Character;
import puertoRico.bot.BotRandom;
import puertoRico.buildings.BuildingAction.Building;
import puertoRico.buildings.BuildingAction.BuildingAction;
import puertoRico.buildings.BuildingIndustrial.IndustrialBuilding;
import puertoRico.plantations.Plantation;
import puertoRico.stock.Stock;
import puertoRico.type.TypeBuildingAction;
import useful.StringJSON;

import java.util.ArrayList;

@JsonIgnoreProperties({"nbUnactiveBuildings","nbUnactivePlantations","fullCity","citySize"})
public class Player implements StringJSON {

    private String name;
    private ArrayList<Building> city;
    private ArrayList<Plantation> farms;
    private Stock inventory;
    private int colonist;
    private Bot bot;
    private boolean winner;
    private boolean gameEven;

    public Player(){

    }


    public Player(String nom, int doublon) {
        this.name = nom;
        city = new ArrayList<>();
        farms = new ArrayList<>();
        this.inventory= new Stock(this,doublon);
        this.colonist=0;
        gameEven=false;
        bot = new BotRandom();
    }

    public Player(String name){

        this.name = name;
        city = new ArrayList<>();
        farms = new ArrayList<>();
        this.colonist=0;
        gameEven=false;
        bot = new BotRandom();
    }


    public void setName(String name) {
        this.name = name;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public Stock getInventory() {
        return inventory;
    }
    public boolean pay(int doublon){
        if(doublon>0&&doublon<=this.inventory.getDoublons())
        {
            return true;
        }
        return false;
    }

    public void setInventory(Stock inventory) {
        this.inventory = inventory;
    }

    public ArrayList<Building> getCity() {
        return city;
    }

    public int getCitySize(){
        int res=0;
        for (Building b: city) {
            res+=b.getSpaceBuilding();
        }
        return res;
    }

    public ArrayList<IndustrialBuilding> getIndustrialBuildings() {
        ArrayList<IndustrialBuilding> idB = new ArrayList<>();
        for (Building b:city)
        {
            if(b instanceof IndustrialBuilding)
                idB.add((IndustrialBuilding)b);
        }
        return idB;
    }


    public String getName() {
        return name;
    }

    public boolean activeBuilding(TypeBuildingAction typeBuildingAction){
        for(Building b : getCity()){
            if(b instanceof BuildingAction){
                if(typeBuildingAction.toString().equals(((BuildingAction) b).getTypeBuildingAction().toString())&&b.isOccupied()){
                    return true;
                }
            }
        }
        return false;
    }

    public void setCity(ArrayList<Building> city) {
        this.city = city;
    }

    public ArrayList<Plantation> getFarms() {
        return farms;
    }

    public void setFarms(ArrayList<Plantation> farms) {
        this.farms = farms;
    }

    public void playRole(Character character) {
        character.activate(this);
    }


    public boolean hasFullCity()
    {
        if(this.getCitySize()==12)
            return true;
        return false;
    }

    public boolean hasBuilding(Building building){
        if (building instanceof IndustrialBuilding)
            return hasBuilding((IndustrialBuilding) building);
        else return hasBuilding((BuildingAction)building);
    }

    public boolean hasBuilding(BuildingAction builing){
        for (Building b:city) {
            if(builing.getName().equals(b.getName()))
                return true;
        }
        return false;
    }
    public boolean hasBuilding(IndustrialBuilding builing){
        int res=0;
        for (Building b:city) {
            if(builing.getName().equals(b.getName()))
                res++;
        }
        if(res>3)
            return true;
        return false;
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
        bot.setPlayer(this);
    }

    public int getColonist() {
        return colonist;
    }

    public void setColonist(int colonist) {
        this.colonist = colonist;
    }

    public void addColonist(){this.colonist++;}

    public ArrayList<String> putColonist(){
        ArrayList<String> res=new ArrayList<>();
        if(getNbUnactiveBuildings()==0 && getNbUnactivePlantations()==0) return res;
        while((getNbUnactiveBuildings()>0 || getNbUnactivePlantations()>0) && colonist>0) {
            int randomColonistBuildings = bot.chooseRandomNumberColonist(getNbUnactiveBuildings());
            for (int i = 0; i < randomColonistBuildings; i++) {
                if (colonist > 0) {
                    Building b = bot.choosePlaceColonistBuilding(getCity());
                    b.putColonist();
                    res.add(b.getName());
                    colonist--;
                } else {
                    break;
                }

            }
            int randomColonistPlantations = bot.chooseRandomNumberColonist(getNbUnactivePlantations());
            for (int i = 0; i < randomColonistPlantations; i++) {
                if (colonist > 0) {
                    Plantation p = bot.choosePlaceColonistPlantation(getFarms());
                    p.putColonist();
                    res.add(p.getName());
                    colonist--;
                } else {
                    break;
                }

            }

        }


        return res;
    }



    public JSONObject convertToJson() throws JSONException {

        JSONObject infoPlayer = new JSONObject();
        infoPlayer.put("namePlayer", this.name);
        infoPlayer.put("strategyPlayer",bot.getName());
        infoPlayer.put("doublons", this.getInventory().getDoublons());
        infoPlayer.put("winner",this.winner);
        infoPlayer.put("gameEven",this.gameEven);
        infoPlayer.put("victoryPoint",this.getInventory().getVictoryPoints());

        return infoPlayer;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name : "+this.name +"\n");
        stringBuilder.append("Strategy player : "+this.bot.getName() +"\n");
        return super.toString();
    }

    public int getNbUnactiveBuildings()
    {
        int res=0;
        for (Building b: this.city ) {
            if(!b.isOccupied())
                res++;
        }
        return res;
    }


    public int getNbUnactivePlantations()
    {
        int res=0;
        for (Plantation p: this.farms ) {
            if(!p.isOccupied())
                res++;
        }
        return res;
    }

    public boolean isGameEven() {
        return gameEven;
    }

    public void setGameEven(boolean gameEven) {
        this.gameEven = gameEven;
    }

}
