package core.characters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * TODO – the PlayerManager should inherit from here
 */
public class CharacterManager {

    /*
     * Use case class that handles updating instances of GameCharacter based on inputs from the InputHandler
     * @param character: An instance of GameCharacter being updated
     * @param type: Either Player or AI so the input handler can distinguish between usages
     * TODO: Change item to type Item when implemented
     * */

    public HashMap<Integer, GameCharacter> characterEntities;
    private ArrayList<Integer> keylist;

    public CharacterManager() {
        this.characterEntities = new HashMap<Integer, GameCharacter>();
        this.keylist = new ArrayList<Integer>();
    }

    public void addCharacter(GameCharacter character) {
        /*
        * Player will always be hashed as 0
        * Defenders will be hashed as even ints
        * Enemies will be hashed as odd ints
        * TODO: Refactor
        * */
        int maxElement = this.keylist.get(this.keylist.size() - 1);

        if (Objects.equals(character.team, "player")) {
            this.characterEntities.put(0, character);
            this.keylist.add(0);
        } else if (Objects.equals(character.team, "defender")) {
            if (maxElement % 2 == 0) {
                this.characterEntities.put(maxElement + 2, character);
                this.keylist.add(maxElement + 2);
            } else {
                this.characterEntities.put(maxElement + 1, character);
                this.keylist.add(maxElement + 1);
            }
        } else {
            if (maxElement % 2 == 0) {
                this.characterEntities.put(maxElement + 1, character);
                this.keylist.add(maxElement + 1);
            } else {
                this.characterEntities.put(maxElement + 2, character);
                this.keylist.add(maxElement + 2);
            }
        }
    }

    public void moveCharacter(int id, float newX, float newY) {
        /*
        * Updates the position of the character
        * */
        if (verifyId(id)) {
            this.characterEntities.get(id).position[0] = newX;
            this.characterEntities.get(id).position[1] = newY;
        }
    }

    public void depleteHealth(int id, int damage) {
        /*
         * Decreases character health by damage
         * */
        if (verifyId(id)) {
            this.characterEntities.get(id).health -= damage;
        }
    }

    public void increaseLevel(int id) {
        /*
         * Increases the level of a character following the completion of a wave
         * */
        if (verifyId(id)) {
            this.characterEntities.get(id).level += 1;
        }
    }

    public boolean canUseItem(int id, String item) {
        /*
         * Checks if the item is in the characters inventory and then returns true if it is.
         * Ensures that there are no issues when controller class calls a child of itemUsageDelegate
         * */
        if (verifyId(id)) {
            return this.characterEntities.get(id).inventory.contains(item);
        }
        return false;
    }

    public void addInventory(int id, String item) {
        /*
         * Adds item to the inventory
         * */
        if (verifyId(id)) {
            this.characterEntities.get(id).inventory.add(item);
        }
    }

    public boolean removeInventory(int id, String item) {
        /*
         * Checks if item is in inventory, then removes if it is
         * Returns True if item successfully removed, false if not
         * */
        if (verifyId(id)) {
            if (this.characterEntities.get(id).inventory.contains(item)) {
                this.characterEntities.get(id).inventory.remove(item);
                return true;
            }
        }
        return false;
    }

    public Object openInventory(int id) {
        /*
         * Returns inventory contents and displays them
         * Returns null if character id cannot be found
         * */
        if (verifyId(id)) {
            return this.characterEntities.get(id).inventory;
        }
        return null;
    }

    private boolean verifyId(int id) {
        // Loops through hashmap to ensure we aren't looking up ids that don't exist
        for (var i : this.characterEntities.entrySet()) {
            if (i.getKey() == id) {
                return true;
            }
        }
        return false;
    }
}
