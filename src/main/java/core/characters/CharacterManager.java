package core.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.Collections;
import core.InventorySystem.*;

/**
 * TODO – the PlayerManager should inherit from here
 */
public class CharacterManager {

    /*
     * Use case class that handles updating instances of GameCharacter based on inputs from the InputHandler
     * @param characterEntities: Hashmap storing characters as values with their UUID as keys
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
         * TODO: Update to use setPosition when worldEntity merged
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

    /***
     *This section implements character behaviors in relation to their inventory
     */

    public boolean canUseItem(UUID id, Item item) {
        /*
         * Checks if the item is in the character's inventory and then returns true if it is.
         * Ensures that there are no issues when controller class calls a UsageDelegate
         * */
        if (verifyId(id)) {
            return this.characterEntities.get(id).getInventory().contains(item);
        }
        return false;
    }

    public boolean selectItem(UUID id, Item item) {
        /*
         * Checks if item is in inventory, then moves it to the first index at which item would be used
         * Returns True if item successfully selected, false if not
         * */
        if (verifyId(id)) {
            if (this.characterEntities.get(id).getInventory().contains(item)) {
//                Collections.swap(this.characterEntities.get(id).getInventory(), 0,
//                        this.characterEntities.get(id).inventory.indexOf(item));
                return true;
            }
        }
        return false;
    }

    public void addInventory(UUID id, Item item) {
        /*
         * Adds item to the inventory
         * */
        if (verifyId(id)) {
            this.characterEntities.get(id).getInventory().add(item);
        }
    }

    public boolean removeInventory(UUID id, Item item) {
        /*
         * Checks if item is in inventory, then removes if it is
         * Returns True if item successfully removed, false if not
         * */
        if (verifyId(id)) {
            if (this.characterEntities.get(id).getInventory().contains(item)) {
                this.characterEntities.get(id).getInventory().remove(item);
                return true;
            }
        }
        return false;
    }

    public Object openInventory(UUID id) {
        /*
         * Returns inventory contents and displays them
         * Returns null if character id cannot be found
         * */
        if (verifyId(id)) {
            return this.characterEntities.get(id).getInventory();
        }
        return null;
    }

    private void acceptInput(UUID id, boolean use) {
        /*
         * calls methods depending on InputController actions //TODO merge with repositioning side of functionality
         * */
        if (use) {
            WeaponUsageDelegate usageDelegate = new WeaponUsageDelegate(id);
            usageDelegate.use((Weapon) this.characterEntities.get(id).getInventory().get(0));
        }
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

