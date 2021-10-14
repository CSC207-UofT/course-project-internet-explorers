package drivers;
import game_characters.*;

import java.util.ArrayList;
// Need to import InputManager and ControlState

public class CharacterManager {

    /*
    * Use case class that handles updating instances of GameCharacter based on inputs from the InputHandler
    * @param character: An instance of GameCharacter being updated
    * TODO: Change item to type Item when implemented
    * TODO: Import InputManager and ControlState
    * */

    private GameCharacter character;

    public CharacterManager(GameCharacter character) {
        this.character = character;
    }

    public void getInput() {
        // Takes inputs from the InputManager and stores them as a ControlState
    }

    public void moveCharacter() {
        // Updates the characters position
    }

    public void depleteHealth(int damage) {
        /*
        * Decreases player health by damage
        * */
        this.character.health -= damage;
    }

    public void increaseLevel() {
        /*
        * Increases the level of enemies by 1 following each wave of enemies.
        * Need to check instance of enemies
        * */
        this.character.level += 1;
    }

    public boolean useItem() {
        /*
        * Checks if the item is in the characters inventory and then allows them to use it.
        * This will only be available to the player class, will check insanceof Player.
        * Returns true iff the player uses the item, false otherwise.
        * */

        return true;
    }

    public void addInventory(String item) {
        /*
        * Adds item to the inventory
        * Only valid for the player class, will check insanceof Player.
        * */
    }

    public boolean removeInventory(String item) {
        /*
         * Checks if item is in inventory, then removes if it is
         * Only valid for the player class, will check insanceof Player.
         * Returns True if item successfully removed
         * */

        return true;
    }

    public ArrayList<String> openInventory() {
        /*
        * Returns inventory contents and displays them
        * */

        // Update to show on display
        return this.character.inventory;
    }

}
