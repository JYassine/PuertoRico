package Boat;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puertoRico.boat.Boat;
import puertoRico.type.TypeWare;

public class BoatTest {

    Boat boat = new Boat(1);

    @BeforeEach
    public void setUp(){
        boat.setMaximumCapacity(6);
    }

    @Test
    public void boatImportTest() {

        Assert.assertEquals(true, boat.importWare(TypeWare.INDIGO, 5));
        Assert.assertEquals(false,boat.importWare(TypeWare.INDIGO,10));
    }



    @Test
    public  void boatSendWare(){

        boat.importWare(TypeWare.INDIGO,6);
        Assert.assertEquals(true,boat.sendWare());
        boat.importWare(TypeWare.INDIGO,5);
        Assert.assertEquals(false,boat.sendWare());

    }

    /**
     * Verify that boat can import only one type of ware
     */
    @Test
    public void boatImportOneTypeWare(){
        Boat boat = new Boat(1);
        boat.setMaximumCapacity(6);
        boat.importWare(TypeWare.INDIGO,3);
        boat.importWare(TypeWare.CORN,2);

        Assert.assertEquals(true,boat.getTypeWare().toString().equals(TypeWare.INDIGO.toString()));
        Assert.assertEquals(true,boat.getSize()==3);


    }

}
