package puertoRico.characters;

import useful.GameMessage;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import puertoRico.type.TypeBuildingAction;
import puertoRico.type.TypeWare;

public class Trader extends Character {

    public int getChamberSize() {
        return chamberSize;
    }

    public void setSellChamber(Stock sellChamber) {
        this.sellChamber = sellChamber;
    }

    public int getChamberStock() {
        return chamberStock;
    }

    public void setChamberStock(int chamberStock) {
        this.chamberStock = chamberStock;
    }

    public int getNbPlayer() {
        return nbPlayer;
    }

    public void setNbPlayer(int nbPlayer) {
        this.nbPlayer = nbPlayer;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    private final int chamberSize = 4;
    private Stock sellChamber = new Stock(null,0);
    private int chamberStock;
    private int nbPlayer;
    private int turn;

    public Trader(){

    }

    public Trader(Stock stock, int nbPlayer) {
        super("Trader", stock);
        chamberStock = 0;
        this.nbPlayer = nbPlayer;
        this.turn = 0;
    }

    public Stock getSellChamber() {
        return sellChamber;
    }

    @Override
    public void activate(Player p) {
        giveBonus(p);
        turn++;
        if (chamberStock != chamberSize) {
            for (TypeWare t : TypeWare.values()) {
                if ((p.getInventory().getStockResource(t) > 0 && (sellChamber.getStockResource(t) == 0)||p.activeBuilding(TypeBuildingAction.OFFICE))) {
                    if (p.getInventory().removeFromStock(t, 1) == 1) {
                        int d = getStock().removeDoublon(t.getValue())+buildingAction(p);
                        p.getInventory().addDoublon(d);
                        this.sellChamber.addInStock(t, 1);
                        GameMessage.messageWhenSell(p, t, d);
                        chamberStock++;
                        if(turn==nbPlayer&&chamberSize==chamberStock)
                        {
                            turn=0;
                            emptySellChamber();
                        }
                        break;
                    }
                }
            }
            if (turn == nbPlayer)
                turn = 0;
        } else if (turn == nbPlayer) {
            turn = 0;
            emptySellChamber();
        }

    }

    private void emptySellChamber() {
        for (TypeWare t : TypeWare.values()) {
            int max=sellChamber.getStockResource(t);
            if (sellChamber.removeFromStock(t, max) == max)
                getStock().addInStock(t, max);
        }
        chamberStock = 0;
        GameMessage.messageWhenSellChamberEmptyed();
    }

    private int buildingAction(Player p) {
        int d = 0;
        if (p.activeBuilding(TypeBuildingAction.SMALLMARKET))
            d += getStock().removeDoublon(1);
        if (p.activeBuilding(TypeBuildingAction.BIGMARKET))
            d+=2;
        return d;
    }

}
