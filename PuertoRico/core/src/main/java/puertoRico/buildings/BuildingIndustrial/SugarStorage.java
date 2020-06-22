package puertoRico.buildings.BuildingIndustrial;

import puertoRico.type.TypeWare;

public class SugarStorage extends IndustrialBuilding{

    public SugarStorage(){
        super(TypeWare.SUGAR);
        this.spaceBuilding=1;
        this.price=2;
        this.victoryPoints=4;
        this.nbAvailable=6;
        setName("Sugar Storage");
    }

}
