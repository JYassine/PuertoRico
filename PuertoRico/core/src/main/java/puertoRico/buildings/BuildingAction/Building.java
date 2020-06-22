package puertoRico.buildings.BuildingAction;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.json.JSONException;
import org.json.JSONObject;
import puertoRico.buildings.BuildingIndustrial.IndustrialBuilding;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "name")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BuildingAction.class, name = "BuildingAction"),
        @JsonSubTypes.Type(value = IndustrialBuilding.class, name = "IndustrialBuilding")

})
public abstract class Building implements Cloneable {

    protected int idBuilding;
    protected int spaceBuilding;
    protected int price;
    protected int victoryPoints;
    protected String name;
    protected boolean occupied;
    protected int nbAvailable;

    public  Building(){
        occupied=false;
    }

    public int getIdBuilding() {
        return idBuilding;
    }

    public int getSpaceBuilding() {
        return spaceBuilding;
    }

    public int getPrice() {
        return price;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getNbAvailable() { return nbAvailable; }

    public void setIdBuilding(int idBuilding) {
        this.idBuilding = idBuilding;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setVictory(int victoryPoints) { this.victoryPoints = victoryPoints; }

    public void setSpaceBuilding(int spaceBuilding) {
        this.spaceBuilding = spaceBuilding;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString()
    {
        return name;
    }

    public boolean putColonist()
    {
        if (occupied == false) {
            occupied = true;
            return true;
        }
        return false;
    }

    public void remove(){
        this.nbAvailable--;
    }

    @Override
    public boolean equals(Object v) {
        boolean retVal = false;

        if (v instanceof Building){
            Building ptr = (Building) v;
            if(ptr.name==((Building) v).name){
                retVal=true;
            }
        }

        return retVal;
    }

    public JSONObject convertToJson() throws JSONException {


        JSONObject infoBuilding = new JSONObject();
        infoBuilding.put("idBuilding", this.idBuilding);
        infoBuilding.put("nbAvailable", this.nbAvailable);
        infoBuilding.put("occupied", this.occupied);
        infoBuilding.put("price", this.price);
        infoBuilding.put("spaceBuilding", this.spaceBuilding);
        infoBuilding.put("victoryPoints", this.victoryPoints);
        infoBuilding.put("name",this.name);

        return infoBuilding;

    }




    public boolean isOccupied(){return occupied;}
    public void setOccupied(boolean bool){occupied=bool;}

    @Override
    public Building clone()  throws CloneNotSupportedException {return (Building)super.clone();}
}

