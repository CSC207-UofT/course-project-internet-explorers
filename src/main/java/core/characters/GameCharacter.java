package core.characters;

import java.util.ArrayList;

public class GameCharacter extends WorldEntity {

    /*
     * Class that defines the main attributes of the classes Player, Enemy and Defender in the game.
     * @param health: The current health the GameCharacter has
     * @param level: The level of the GameCharacter
     * @param position: The current position of the GameCharacter on the map
     * @param inventory: The Items the GameCharacter is able to use at a given point in the game
     * @param position: the x, y coordinates of the entity on the map
     * @param shape: the shape of the object, used to define movement and hit box.
     * TODO: Change type of ArrayList to Item
     * */

    public String team;
    public int health;
    public int level;
    public ArrayList<String> inventory;

    public GameCharacter(float[] position, String shape, String team, int health, int level,
                         ArrayList<String> inventory) {
        super(position, shape);
        this.team = team;
        this.health = health;
        this.level = level;
        this.inventory = inventory;
    }
}
