package puertoRico.characters;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import useful.GameMessage;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import useful.StringJSON;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "name")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Builder.class, name = "Builder"),
        @JsonSubTypes.Type(value = Captain.class, name = "Captain"),
        @JsonSubTypes.Type(value = Craftman.class, name = "Craftman"),
        @JsonSubTypes.Type(value = Farmer.class, name = "Farmer"),
        @JsonSubTypes.Type(value = Mayor.class, name = "Mayor"),
        @JsonSubTypes.Type(value = Trader.class, name = "Trader")

})

public abstract class Character implements Action, StringJSON {

    protected String name;
    protected int bonus;
    protected Stock stock;

    public Character(){

    }
    public Character(String name,Stock stock){
        this.name=name;
        this.stock=stock;
        bonus=0;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBonus() {
        return bonus;
    }

    public Stock getStock(){ return stock; }

    public String toString()
    {
        return name;
    }

    public void bonusIncrement() {this.bonus++;}

    public void giveBonus(Player p)
    {
        if (bonus>0) {
            int d=this.stock.removeDoublon(bonus);
            GameMessage.messageWhenBonus(p,d);
            p.getInventory().addDoublon(d);
        }
        bonus=0;
    }

    @Override
    public boolean equals(Object obj) {
        Character characterObj = (Character) obj;

        if(this.name.equals(characterObj.name)){
            return true;
        }else{
            return false;
        }
    }
}
