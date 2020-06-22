package Bot;
import gameServer.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puertoRico.boat.Boat;
import puertoRico.bot.Bot;
import puertoRico.bot.BotCaptain;
import puertoRico.characters.Character;
import puertoRico.player.Player;
import puertoRico.stock.Stock;
import puertoRico.type.TypeWare;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;

public class BotCaptainTest {

    private Bot bot;
    private ArrayList<Boat> boats;
    private Stock stock;
    private ArrayList<Character> characters;

    @BeforeEach
    public void setUp(){

        boats = new ArrayList<>();
        Boat b1 = new Boat(1);
        b1.setMaximumCapacity(6);
        Boat b2 = new Boat(2);
        b2.setMaximumCapacity(5);
        Boat b3 = new Boat(3);
        b3.setMaximumCapacity(4);
        boats.add(b1);
        boats.add(b2);
        boats.add(b3);
        characters = new ArrayList<>();

        stock = new Stock(3);
        bot = new BotCaptain();

    }


    @Test
    /**
     * We test if the bot choose the Captain if the player have one type of ware that he can export
     */
    public void botReturnCaptain(){

        Player p = new Player("Joueur",0);
        p.getInventory().addInStock(TypeWare.CORN,5);
        Game game = new Game();
        p.setBot(bot);
        Character c =  bot.chooseCharacter(game.getCharacterList());

        assertEquals(true,c.getName().equals("Captain"));

    }

    @Test
    /**
     * We test if the bot choose another Character if he don't have enough ware to export
     */
    public void botReturnAnotherCharacter(){

            Player p = new Player("Joueur",0);
            p.getInventory().addInStock(TypeWare.CORN,0);
            Game game = new Game();
            p.setBot(bot);
            Character c =  bot.chooseCharacter(game.getCharacterList());
            assertEquals(true,!c.getName().equals("Captain"));



    }


}
