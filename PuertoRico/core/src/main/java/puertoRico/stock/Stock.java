package puertoRico.stock;

import org.json.JSONException;
import org.json.JSONObject;
import puertoRico.player.Player;
import puertoRico.type.TypeWare;

import java.util.HashMap;
import java.util.Map;

public class Stock {

    public HashMap<TypeWare, Integer> getStock() {
        return stock;
    }

    public void setStock(HashMap<TypeWare, Integer> stock) {
        this.stock = stock;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    private HashMap<TypeWare,Integer> stock;
    private int doublons;
    private int colonists;
    private int victoryPoints;

    public Stock(){

    }
    public Stock(int nbPlayers){
        stock = new HashMap<>();
        stock.put(TypeWare.CORN,10);
        stock.put(TypeWare.INDIGO,11);
        stock.put(TypeWare.SUGAR,11);
        stock.put(TypeWare.COFFEE,9);
        stock.put(TypeWare.TOBACCO,9);
        switch (nbPlayers){
            case 4:
                doublons=74;
                victoryPoints=100;
                this.colonists=75;
                break;
            case 5:
                doublons=66;
                victoryPoints=122;
                this.colonists=95;
                break;
            default:
                doublons=80;
                victoryPoints=75;
                this.colonists=55;
                break;
        }
    }

    public Stock(Player p,int doublons){
        stock = new HashMap<>();
        stock.put(TypeWare.CORN,0);
        stock.put(TypeWare.INDIGO,0);
        stock.put(TypeWare.SUGAR,0);
        stock.put(TypeWare.COFFEE,0);
        stock.put(TypeWare.TOBACCO,0);
        this.victoryPoints=0;
        this.colonists=0;
        this.doublons=doublons;
    }

    public JSONObject convertToJson() throws JSONException {
        JSONObject stockJSON = new JSONObject();
        JSONObject hashMapStock = new JSONObject();
        for (Map.Entry<TypeWare, Integer> entry : stock.entrySet()) {
            hashMapStock.put(String.valueOf(entry.getKey()), (String.valueOf(entry.getValue())));
        }
        stockJSON.put("stock",hashMapStock);
        stockJSON.put("doublons",doublons);
        stockJSON.put("colonists",colonists);
        stockJSON.put("victoryPoints",victoryPoints);

        return stockJSON;

    }


    public int getStockResource(TypeWare resources){
        return stock.get(resources);
    }
    public int getDoublons(){ return doublons;}
    public void setDoublons(int doublons){this.doublons=doublons;}
    public int getVictoryPoints() { return victoryPoints; }
    public int getColonists(){return colonists;}

    public void setColonists(int colonists) {
        this.colonists = colonists;
    }

    public void addInStock(TypeWare resources, int number){
        if(number>0 ) {
            int oldStock = stock.get(resources);
            int newStock = oldStock + number;
            stock.put(resources, newStock);
        }
    }

    public int removeFromStock(TypeWare resources, int number){
        if(number>0 &&  stock.get(resources) - number >= 0)
        {
            int n = Math.min(number,stock.get(resources));
            int oldStock = stock.get(resources);
            int newStock = oldStock-n;
            stock.put(resources,newStock);
            return n;
        }
        return 0;
    }

    public int removeDoublon(int d){
        if(d>0)
        {
            int max=Math.min(d,doublons);
            doublons-=max;
            return max;
        }
        return 0;
    }

    public void addDoublon(int d){
        if(d>0){
            this.doublons+=d;
        }
    }

    public int removeVictoryPoints(int v){
        if(v>0)
        {
            int max=Math.min(v,victoryPoints);
            victoryPoints-=max;
            return max;
        }
        return 0;
    }

    public void addVictoryPoints(int v){
        if(v>=0){
            this.victoryPoints+=v;
        }
    }

    public int removeColonist(int c){
        if(c>0)
        {
            int max=Math.min(c,colonists);
            colonists-=max;
            return max;
        }
        return 0;
    }

    public void addColonist(int c){
        if(c>0){
            this.doublons+=c;
        }
    }
}
