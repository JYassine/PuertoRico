package puertoRico.buildings.BuildingAction;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import puertoRico.type.TypeBuildingAction;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "name")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BigMarket.class, name = "BigMarket"),
        @JsonSubTypes.Type(value = Hospice.class, name = "Hospice"),
        @JsonSubTypes.Type(value = Office.class, name = "Office"),
        @JsonSubTypes.Type(value = SmallMarket.class, name = "SmallMarket"),
        @JsonSubTypes.Type(value = University.class, name = "University")

})
public abstract class BuildingAction extends Building {

    private TypeBuildingAction typeBuildingAction;

    public BuildingAction(){
        this.name="BuildingAction";
    }

    public TypeBuildingAction getTypeBuildingAction() {
        return typeBuildingAction;
    }

    public void setTypeBuildingAction(TypeBuildingAction typeBuildingAction) {
        this.typeBuildingAction = typeBuildingAction;
    }
}
