package puertoRico.bot;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.json.JSONException;
import org.json.JSONObject;
import puertoRico.boat.Boat;
import puertoRico.buildings.BuildingAction.Building;
import puertoRico.characters.Character;
import puertoRico.plantations.Plantation;
import puertoRico.player.Player;
import puertoRico.type.TypeWare;
import java.util.ArrayList;
import java.util.Random;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "name")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BotBuilding.class, name = "botBuilding"),
        @JsonSubTypes.Type(value = BotCaptain.class, name = "botCaptain"),
        @JsonSubTypes.Type(value = BotRandom.class, name = "botRandom")

})
public  abstract class Bot {

    public void setName(String name) {
        this.name = name;
    }

    protected Random random;
      protected String name;
      public Bot(){
            this.random= new Random();
            name="Undefined";
      }
     public Building chooseBuilding(ArrayList<Building> buildings) {
            int randomBuilding = getRandom().nextInt(buildings.size());
            return buildings.get(randomBuilding);
      }

    public JSONObject convertToJson() throws JSONException {

        JSONObject infoBot = new JSONObject();
        infoBot.put("name", this.name);


        return infoBot;
    }


      public Plantation choosePlantation(ArrayList<Plantation> plantations) {
            int randomPlantation = getRandom().nextInt(plantations.size());
            return plantations.get(randomPlantation);
      }

      public Character chooseCharacter(ArrayList<Character> characters) {
            int randomCharacter = getRandom().nextInt(characters.size());
            return  characters.get(randomCharacter);
      }

      public TypeWare chooseTypeWare(ArrayList<TypeWare> typeWares) {
            int randomResources = getRandom().nextInt(typeWares.size());
            return typeWares.get(randomResources);
      }



      public Boat chooseBoat(ArrayList<Boat> boats) {
            int randomBoat = getRandom().nextInt(boats.size());
            return  boats.get(randomBoat);
      }


      public Building choosePlaceColonistBuilding(ArrayList<Building> buildings) {
            ArrayList<Building> buildingsNonActivated = new ArrayList<>();
            for(Building building : buildings){
                  if(!building.isOccupied()){
                        buildingsNonActivated.add(building);
                  }
            }
            return chooseBuilding(buildingsNonActivated);
      }


      public Plantation choosePlaceColonistPlantation(ArrayList<Plantation> plantations) {
            ArrayList<Plantation> plantationsNonActivated = new ArrayList<>();
            for(Plantation plantation : plantations){
                  if(!plantation.isOccupied()){
                        plantationsNonActivated.add(plantation);
                  }
            }
            return choosePlantation(plantationsNonActivated);
      }


      public int chooseRandomNumberColonist(int numberPlace) {

            if(numberPlace==0){
                  return 0;
            }
            int numberColonist = getRandom().nextInt(numberPlace)+1;
            return numberColonist;
      }

      protected Random getRandom(){
            return random;
      }

      public void setRandom(Random random) {
            this.random = random;
      }

      public String getName(){
            return name;
      }
      public abstract Player getPlayer(Player p);
      public abstract void setPlayer(Player p);



}
