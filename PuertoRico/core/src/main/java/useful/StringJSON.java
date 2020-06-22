package useful;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface StringJSON {

    default String serialize(){
        ObjectMapper Obj = new ObjectMapper();
        String stringJSON="";
        try {
            stringJSON = Obj.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return stringJSON;
    }
}
