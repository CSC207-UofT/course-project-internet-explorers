package game_characters;

import game.WorldEntity;

public class Defender extends GameCharacter implements WorldEntity {
    /*
     * Tower defenders that will be placed on the map by Player
     * @param health: The current health the defender has
     * @param level: The level of the defender
     * @param inventory: The Items the GameCharacter is able to use at a given point in the game
     * */

    public Defender(int health, int level) {
        super(health, level);
    }
}
