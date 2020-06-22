package puertoRico.buildings.BuildingAction;

import puertoRico.type.TypeBuildingAction;

public class Hospice extends BuildingAction{

    public Hospice() {
        super();
        this.spaceBuilding=1;
        this.price=4;
        this.victoryPoints=6;
        this.idBuilding=2;
        this.nbAvailable=2;
        setTypeBuildingAction(TypeBuildingAction.HOSPICE);
        setName(TypeBuildingAction.HOSPICE.toString());
    }

}
