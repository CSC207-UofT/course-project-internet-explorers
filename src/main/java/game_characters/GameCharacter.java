package game_characters;

import java.util.ArrayList;

public abstract class GameCharacter {
    /*
    * Abstract class that defines the main attributes of the classes Player, Enemy and Defender in the game.
    * @param health: The current health the GameCharacter has
    * @param level: The level of the GameCharacter
    * @param position: The current position of the GameCharacter on the map
    * @param inventory: The Items the GameCharacter is able to use at a given point in the game
    * */

    public int health;
    public int level;
    // Need to change type to Item when implemented
    public ArrayList<String> inventory;

    public GameCharacter(int health, int level, ArrayList<String> inventory) {
        this.health = health;
        this.level = level;
        this.inventory = new ArrayList<String>();
    }

    public GameCharacter(int health, int level) {
        this.health = health;
        this.level = level;
    }

}
