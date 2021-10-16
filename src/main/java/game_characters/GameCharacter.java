package game_characters;

import java.util.ArrayList;

public abstract class GameCharacter {
    /*
    * Abstract class that defines the main attributes of the classes Player, Enemy and Defender in the game.
    * @param health: The current health the GameCharacter has
    * @param level: The level of the GameCharacter
    * @param position: The current position of the GameCharacter on the map
    * @param inventory: The Items the GameCharacter is able to use at a given point in the game
    * @param position: the x, y coordinates of the entity on the map
    * @param shape: the shape of the object, used to define movement and hit box.
    * */

    public String team;
    public int health;
    public int level;
    // Need to change type to Item when implemented
    public ArrayList<String> inventory;
    public double[][] position;
    private final String shape;

    public GameCharacter(int health, int level, ArrayList<String> inventory, String shape) {
        this.health = health;
        this.level = level;
        this.inventory = new ArrayList<String>();
        this.shape = shape;
    }

    public GameCharacter(String team, int health, int level, String shape) {
        this.team = team;
        this.health = health;
        this.level = level;
        this.shape = shape;
    }

    public String getShape() {
        return this.shape;
    }

}
