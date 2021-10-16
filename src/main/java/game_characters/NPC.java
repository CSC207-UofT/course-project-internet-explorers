package game_characters;

public class NPC extends GameCharacter {
    /*
     * Tower defenders that will be placed on the map by Player
     * @param health: The current health the defender has
     * @param level: The level of the defender
     * @param position: the x, y coordinates of the entity on the map
     * @param shape: the shape of the object, used to define movement and hit box.
     * TODO: Add that the class implements DamageableCollidable when added
     * */

    public NPC(String team, int health, int level, String shape) {
        super(team, health, level, shape);
        this.position = new double[0][0];
    }
}
