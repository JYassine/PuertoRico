package puertoRico.boat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import puertoRico.type.TypeWare;

import java.util.ArrayList;

@JsonIgnoreProperties({ "size","full" })
public class Boat {

    public void setStock(ArrayList<TypeWare> stock) {
        this.stock = stock;
    }

    public void setNumberBoat(int numberBoat) {
        this.numberBoat = numberBoat;
    }

    private ArrayList<TypeWare> stock;
    private int numberBoat;
    private int maximumCapacity;
    private TypeWare typeWare;


    public Boat(){

    }
    public Boat(int numberBoat) {
        this.stock = new ArrayList<TypeWare>();
        this.numberBoat=numberBoat;
    }


    public int getNumberBoat() {
        return numberBoat;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public boolean isFull(){
        return stock.size()==maximumCapacity;
    }

    public ArrayList<TypeWare> getStock() {
        return stock;
    }

    public boolean importWare(TypeWare t, int nb) {

        if( nb<=6 && (stock.size()+nb<=maximumCapacity)) {
            if(typeWare==null){
                typeWare=t;
            }else if(typeWare!=t){
                return false;
            }
            for (int i = 0; i < nb; i++) {
                stock.add(t);

            }

            return true ;
        }
        else  return false;
    }


    public boolean  sendWare(){
        if (stock.size()==maximumCapacity){
            stock.clear();
            typeWare=null;
            return true;}

        else  return false;

    }

    public int getSize(){
        return stock.size();
    }

    public TypeWare getTypeWare() {
        return typeWare;
    }

    public void setTypeWare(TypeWare typeWare) {
        this.typeWare = typeWare;
    }
}


