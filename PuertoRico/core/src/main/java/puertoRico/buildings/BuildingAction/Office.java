package puertoRico.buildings.BuildingAction;

import puertoRico.type.TypeBuildingAction;

public class Office extends BuildingAction{

    public Office() {
        super();
        this.spaceBuilding=1;
        this.price=5;
        this.victoryPoints=9;
        this.idBuilding=2;
        this.nbAvailable=2;
        setTypeBuildingAction(TypeBuildingAction.OFFICE);
        setName(TypeBuildingAction.OFFICE.toString());

    }
}
