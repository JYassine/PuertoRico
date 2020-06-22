package puertoRico.type;

public enum TypeBuildingAction {

    SMALLMARKET("SmallMarket"),
    OFFICE("Office"),
    BIGMARKET("BigMarket"),
    UNIVERSITY("University"),
    HOSPICE("Hospice");


    private String name;

    TypeBuildingAction(String name){
        this.name=name;
    }

    @Override
    public String toString() {
        return name;
    }
}
