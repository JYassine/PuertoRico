package puertoRico.buildings.BuildingIndustrial;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import puertoRico.buildings.BuildingAction.*;
import puertoRico.type.TypeWare;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "name")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CoffeeRoaster.class, name = "CoffeeRoaster"),
        @JsonSubTypes.Type(value = SmallIndigoPlant.class, name = "SmallIndigoPlant"),
        @JsonSubTypes.Type(value = SugarStorage.class, name = "SugarStorage"),
        @JsonSubTypes.Type(value = TobaccoStorage.class, name = "TobaccoStorage")
})

public abstract class IndustrialBuilding extends Building {

    private TypeWare resources;

    public  IndustrialBuilding(TypeWare t){
        super();
        this.name="IndustrialBuilding";
        this.resources =t;
        this.idBuilding=4;
    }

    public TypeWare getResources()
    {
        return resources;
    }

}
