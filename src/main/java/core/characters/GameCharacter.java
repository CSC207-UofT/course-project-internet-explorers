package core.characters;

import java.util.ArrayList;
import core.InventorySystem.*;

public class GameCharacter extends WorldEntity {

    /*
     * Class that defines the main attributes of the classes Player, Enemy and Defender in the game.
     * @param health: The current health the GameCharacter has
     * @param level: The level of the GameCharacter
     * @param team: either player, defense or enemy to denote enemies and allies
     * @param inventory: The Items the GameCharacter is able to use at a given point in the game
     * TODO: Change type of ArrayList to Item
     * */

    public String team;
    public int health;
    public int level;
    public ArrayList<Item> inventory;

    public GameCharacter(String shape, String team, int health, int level,
                         ArrayList<Item> inventory) {
        super(shape);
        this.team = team;
        this.health = health;
        this.level = level;
        this.inventory = inventory;
    }
}


