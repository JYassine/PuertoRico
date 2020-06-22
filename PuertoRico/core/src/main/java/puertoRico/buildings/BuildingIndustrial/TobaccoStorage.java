package puertoRico.buildings.BuildingIndustrial;

import puertoRico.type.TypeWare;

public class TobaccoStorage extends IndustrialBuilding {


    public TobaccoStorage(){
        super(TypeWare.TOBACCO);
        this.spaceBuilding=1;
        this.price=6;
        this.victoryPoints=9;
        this.nbAvailable=6;
        setName("Tobacco Storage");
    }
}
