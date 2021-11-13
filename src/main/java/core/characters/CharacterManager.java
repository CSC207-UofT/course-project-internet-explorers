package core.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;


public class CharacterManager {

    /*
     * Use case class that handles updating instances of GameCharacter based on inputs from the InputHandler
     * @param characterEntities: Hashmap storing characters as values with their UUID as keys
     * TODO: Change item to type Item when implemented
     * */

    public HashMap<UUID, GameCharacter> characterEntities;

    public CharacterManager() {
        this.characterEntities = new HashMap<UUID, GameCharacter>();
    }

    public void addCharacter(GameCharacter character) {
        /*
        * Generates a unique id for each character when added
        * */
        this.characterEntities.put(character.getId(), character);
    }

    /***
     *This is the use case that sets the velocity for the character with the specific id and moves the character
     * @param id character id
     * @param dx change in x from inputHandler
     * @param dy change in y from inputHandler
     */
    public void updateCharacterPosition(UUID id, float dx, float dy) {

        /*
        * Updates the position of the character
        * */

        if (verifyId(id)){
            this.characterEntities.get(id).move(new Vector2(dx, dy));
        }
    }

    public void updateHealth(UUID id, int damage) {
        /*
         * Decreases character health by damage
         * */
        if (verifyId(id)) {
            this.characterEntities.get(id).setHealth(this.characterEntities.get(id).getHealth() - damage);
        }
    }

    public void updateLevel(UUID id) {
        /*
         * Increases the level of a character following the completion of a wave
         * */
        if (verifyId(id)) {
            this.characterEntities.get(id).setLevel(this.characterEntities.get(id).getLevel() + 1);
        }
    }

    public boolean canUseItem(UUID id, String item) {
        /*
         * Checks if the item is in the characters inventory and then returns true if it is.
         * Ensures that there are no issues when controller class calls a child of itemUsageDelegate
         * */
        if (verifyId(id)) {
            return this.characterEntities.get(id).getInventory().contains(item);
        }
        return false;
    }

    private boolean verifyId(UUID id) {
        // Loops through hashmap to ensure .get doesn't return null
        for (var i : this.characterEntities.entrySet()) {
            if (i.getKey() == id) {
                return true;
            }
        }
        return false;
    }
}
