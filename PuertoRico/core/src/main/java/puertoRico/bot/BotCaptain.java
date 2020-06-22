package puertoRico.bot;
import puertoRico.characters.Character;
import puertoRico.player.Player;
import puertoRico.type.TypeWare;

import java.util.ArrayList;

public class BotCaptain extends Bot {

    private Player player;

    public BotCaptain() {
        super();
        this.name = "botCaptain";
    }


    @Override
    public Character chooseCharacter(ArrayList<Character> characters) {

        int resource= 0;
        int minimumBoatSize =1;
        Character captain =null;

        ArrayList<Character> copyCharacters = new ArrayList<>();
        copyCharacters.addAll(characters);

        for (TypeWare typeWare : TypeWare.values()) {
            resource = player.getInventory().getStockResource(typeWare);
            if (resource >= minimumBoatSize ) {
                for (Character character : copyCharacters) {
                    if (character.getName().equals("Captain")) {
                        captain=character;
                        break;
                    }
                }
            }
        }

        if(captain==null){
            for(int i=0;i<copyCharacters.size();i++){
                if(copyCharacters.get(i).getName().equals("Captain")){
                    copyCharacters.remove(copyCharacters.get(i));
                    break;
                }

            }
            return copyCharacters.get(random.nextInt(characters.size()-1));
        }

        return captain;
    }


    @Override
    public Player getPlayer(Player p) {
        return p;
    }

    @Override
    public void setPlayer(Player p) {
        this.player = p;
    }


}
