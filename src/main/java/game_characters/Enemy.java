package game_characters;

import game.WorldEntity;

public class Enemy extends GameCharacter implements WorldEntity {

    /*
     * Represents enemies in the game that attack the players castle and defenders
     * @param health: The current health the Enemy has
     * @param level: The level of the Enemy
     * TODO: Add that the class implements DamageableCollidable when added
     * */

    public Enemy(int health, int level) {
        super(health, level);
    }
}
