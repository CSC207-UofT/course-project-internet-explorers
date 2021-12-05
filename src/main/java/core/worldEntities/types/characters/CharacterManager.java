package core.worldEntities.types.characters;

import core.input.CharacterInput;
import core.input.CharacterInputDevice;
import core.inventory.Item;
import core.inventory.Weapon;
import core.inventory.WeaponUsageDelegate;
import core.worldEntities.WorldEntityManager;
import java.util.*;

public class CharacterManager {

    /*
     * Use case class that handles updating instances of GameCharacter based on inputs from the InputHandler
     * @param characterEntities: Hashmap storing characters as values with their UUID as keys
     * */

    private final WorldEntityManager entityManager;

    public CharacterManager(WorldEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Updates the GameCharacter's velocity to move character in direction specified by the given input (provided by InputController).
     * Also invokes item usage on the held item (per the input).
     */
    public void processInputs(float dt, Map<UUID, CharacterInput> inputs) {
        inputs.forEach((id, input) -> {
            // normalize input direction then scale by desired speed in m/s
            // Moved this to be a setter in the entity class
            entityManager.getEntity(id).setLinearVelocity(input.direction().nor().scl(10f));

            // TODO separate movement/usage into separate methods (also consult with ben)
            if (input.using()) {
                WeaponUsageDelegate usageDelegate = new WeaponUsageDelegate(id);
                usageDelegate.use((Weapon) ((Character) this.entityManager.getEntity(id)).getInventory().get(0));
            }
        });
    }

    public void setInputDeviceType(UUID id, Class<? extends CharacterInputDevice> inputDeviceType) {
        verifyId(id).setInputDeviceType(inputDeviceType);
    }

    public void updateHealth(UUID id, int damage) {
        /*
         * Decreases character health by damage
         * */
        verifyId(id).setHealth(verifyId(id).getHealth() - damage);
    }

    public void updateLevel(UUID id) {
        /*
         * Increases the level of a character following the completion of a wave
         * */
        verifyId(id).setLevel(verifyId(id).getLevel() + 1);
    }

    /***
     *This section implements character behaviors in relation to their inventory
     */

    public boolean canUseItem(UUID id, Item item) {
        /*
         * Checks if the item is in the character's inventory and then returns true if it is.
         * Ensures that there are no issues when controller class calls a UsageDelegate
         * */
        return verifyId(id).getInventory().contains(item);
    }

    public boolean selectItem(UUID id, Item item) {
        /*
         * Checks if item is in inventory, then moves it to the first index at which item would be used
         * Returns True if item successfully selected, false if not
         * */
        if (verifyId(id).getInventory().contains(item)) {
            Collections.swap(verifyId(id).getInventory(), 0, verifyId(id).getInventory().indexOf(item));
            return true;
        }
        return false;
    }

    public void addInventory(UUID id, Item item) {
        /*
         * Adds item to the inventory
         * */
        verifyId(id).getInventory().add(item);
    }

    public boolean removeInventory(UUID id, Item item) {
        /*
         * Checks if item is in inventory, then removes if it is
         * Returns True if item successfully removed, false if not
         * */
        if (verifyId(id).getInventory().contains(item)) {
            verifyId(id).getInventory().remove(item);
            return true;
        }
        return false;
    }

    public ArrayList<Item> openInventory(UUID id) {
        /*
         * Returns inventory contents and displays them
         * Returns null if character id cannot be found
         * */
        return verifyId(id).getInventory();
    }

    /*
     * Returns the worldEntity casted as a GameCharacter if the entity is an instance
     * Otherwise throws an exception
     * */
    private Character verifyId(UUID id) {
        try {
            return (Character) this.entityManager.getEntity(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Entity with id: " + id + " is not of type GameCharacter.");
        }
    }
}
