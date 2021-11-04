package core.characters;

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

    public void updateCharacterPosition(UUID id, float newX, float newY) {
        /*
        * Updates the position of the character
        * TODO: Update to use setPosition when worldEntity merged
        * */
        if (verifyId(id)) {
            this.characterEntities.get(id).position[0] += newX;
            this.characterEntities.get(id).position[1] += newY;
        }
        if (verifyId(id)) {
            this.characterEntities.get(id).position[0] += newX;
            this.characterEntities.get(id).position[1] += newY;
        }
    }

    public void depleteHealth(UUID id, int damage) {
        /*
         * Decreases character health by damage
         * */
        if (verifyId(id)) {
            this.characterEntities.get(id).setHealth(this.characterEntities.get(id).getHealth() - damage);
        }
    }

    public void increaseLevel(UUID id) {
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
//    TODO: Move inventory stuff to separate inventory manager class
//    public void addInventory(UUID id, String item) {
//        /*
//         * Adds item to the inventory
//         * */
//        if (verifyId(id)) {
//            this.characterEntities.get(id).inventory.add(item);
//        }
//    }
//
//    public boolean removeInventory(UUID id, String item) {
//        /*
//         * Checks if item is in inventory, then removes if it is
//         * Returns True if item successfully removed, false if not
//         * */
//        if (verifyId(id)) {
//            if (this.characterEntities.get(id).inventory.contains(item)) {
//                this.characterEntities.get(id).inventory.remove(item);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public Object openInventory(UUID id) {
//        /*
//         * Returns inventory contents and displays them
//         * Returns null if character id cannot be found
//         * */
//        if (verifyId(id)) {
//            return this.characterEntities.get(id).inventory;
//        }
//        return null;
//    }

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
