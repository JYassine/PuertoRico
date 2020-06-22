package puertoRico.buildings.BuildingIndustrial;

import puertoRico.type.TypeWare;

public class CoffeeRoaster extends IndustrialBuilding {


    public CoffeeRoaster(){
        super(TypeWare.COFFEE);
        this.spaceBuilding=1;
        this.price=7;
        this.victoryPoints=10;
        this.idBuilding=3;
        this.nbAvailable=6;
        setName("Coffee Roaster");
    }
}
