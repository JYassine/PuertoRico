package puertoRico.buildings.BuildingAction;

import puertoRico.type.TypeBuildingAction;

public class University extends BuildingAction{

    public University() {
        super();
        this.spaceBuilding=1;
        this.price=8;
        this.victoryPoints=12;
        this.idBuilding=3;
        this.nbAvailable=2;
        setTypeBuildingAction(TypeBuildingAction.UNIVERSITY);
        setName(TypeBuildingAction.UNIVERSITY.toString());
    }

}
