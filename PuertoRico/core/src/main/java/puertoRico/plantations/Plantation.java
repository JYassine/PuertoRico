package puertoRico.plantations;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.json.JSONException;
import org.json.JSONObject;
import puertoRico.buildings.BuildingAction.*;
import puertoRico.type.TypeWare;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "name")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CoffeePlantation.class, name = "CoffeePlantation"),
        @JsonSubTypes.Type(value = CornPlantation.class, name = "CornPlantation"),
        @JsonSubTypes.Type(value = IndigoPlantation.class, name = "IndigoPlantation"),
        @JsonSubTypes.Type(value = TobaccoPlantation.class, name = "TobaccoPlantation"),
        @JsonSubTypes.Type(value = SugarPlantation.class, name = "SugarPlantation")

})
public abstract class Plantation implements Cloneable{

    protected int idBuilding;
    protected TypeWare resources;
    protected String name;
    protected boolean occupied;

    public Plantation(){

    }

    public  Plantation(TypeWare t){
        this.resources =t;
    }

    public int getIdBuilding() {
        return idBuilding;
    }

    public void setIdBuilding(int idBuilding) {
        this.idBuilding = idBuilding;
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

    public TypeWare getResources() { return resources; }

    public boolean putColonist()
    {
        if (occupied == false) {
            occupied = true;
            return true;
        }
        return false;
    }

    public JSONObject convertToJson() throws JSONException {


        JSONObject infoPlantation = new JSONObject();
        infoPlantation.put("idBuilding", this.idBuilding);
        infoPlantation.put("occupied", this.occupied);
        infoPlantation.put("name", this.name);
        infoPlantation.put("resources", this.resources.toString());
        return infoPlantation;

    }


    @Override
    public boolean equals(Object v) {
        boolean retVal = false;

        if (v instanceof Plantation){
            Plantation ptr = (Plantation) v;
            if(ptr.name==((Plantation) v).name){
                retVal=true;
            }
        }

        return retVal;
    }

    public boolean isOccupied(){return occupied;}

    @Override
    public Plantation clone()  throws CloneNotSupportedException {return (Plantation) super.clone();}
}
