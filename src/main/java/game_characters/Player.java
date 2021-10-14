package game_characters;

import game.WorldEntity;

import java.util.ArrayList;

public class Player extends GameCharacter implements WorldEntity {
    /*
    * The main character of the game that will be controlled through external inputs and interact with the map
     * @param health: The current health the Player has
     * @param level: The level of the Player
     * @param inventory: The Items the GameCharacter is able to use at a given point in the game
     * TODO: Add that the class implements DamageableCollidable when added
     * TODO: Change inventory type to be Item when added
    * */

    public Player(String imgFile, int health, int level, ArrayList<String> inventory) {
        super(imgFile, health, level, inventory);
    }
}
