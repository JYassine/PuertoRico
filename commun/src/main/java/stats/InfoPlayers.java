package stats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class InfoPlayers {
    private ArrayList<InfoPlayer> infoPlayers;

    public InfoPlayers() { infoPlayers = new ArrayList<InfoPlayer>();}

    public JSONObject convertToJson() throws JSONException {
        JSONObject jsonInfoPlayer= new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i =0 ; i < this.getInfoPlayers().size(); i++)
        {
            InfoPlayer infoPlayer = this.getInfoPlayers().get(i);
            JSONObject p = new JSONObject();
            p.put("namePlayer", infoPlayer.getNamePlayer());
            p.put("strategyPlayer", infoPlayer.getStrategyPlayer());
            jsonArray.put(p);
        }

        jsonInfoPlayer.put("infoPlayers",jsonArray);

        return jsonInfoPlayer;

    }

    public ArrayList<InfoPlayer> getInfoPlayers() {
        return infoPlayers;
    }
    public void setInfoPlayers(ArrayList<InfoPlayer> points) {
        this.infoPlayers = points;
    }


}