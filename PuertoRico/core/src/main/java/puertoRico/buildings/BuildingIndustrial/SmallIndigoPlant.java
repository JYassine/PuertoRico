package puertoRico.buildings.BuildingIndustrial;

import puertoRico.type.TypeWare;

public class SmallIndigoPlant extends IndustrialBuilding {


    public SmallIndigoPlant(){
        super(TypeWare.INDIGO);
        this.spaceBuilding=1;
        this.price=1;
        this.victoryPoints=3;
        this.nbAvailable=6;
        setName("Small Indigo Plant");
    }
}
