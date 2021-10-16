package game_characters;

import java.util.ArrayList;

public class Player extends GameCharacter {
    /*
    * The main character of the game that will be controlled through external inputs and interact with the map
     * @param health: The current health the Player has
     * @param level: The level of the Player
     * @param inventory: The Items the GameCharacter is able to use at a given point in the game
     * @param position: the x, y coordinates of the entity on the map
     * @param shape: the shape of the object, used to define movement and hit box.
     * TODO: Add that the class implements DamageableCollidable when added
     * TODO: Change inventory type to be Item when added
    * */

    public Player(int health, int level, ArrayList<String> inventory, String shape) {
        super(health, level, inventory, shape);
        this.position = new double[0][0];
    }
}
