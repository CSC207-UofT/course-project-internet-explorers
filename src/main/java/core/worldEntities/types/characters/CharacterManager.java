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
            // normalize input direction then scale by desired speed
            entityManager.getEntity(id).setLinearVelocity(input.direction().nor().scl(Character.SPEED));

            if (input.using()) {
                WeaponUsageDelegate usageDelegate = new WeaponUsageDelegate(id);
                usageDelegate.use((Weapon) verifyId(id).getInventory().get(0));
            }
        });
    }

    public void setInputDeviceType(UUID id, Class<? extends CharacterInputDevice> inputDeviceType) {
        verifyId(id).setInputDeviceType(inputDeviceType);
    }

    /*
     * Increases the level of a character following the completion of a wave
     * */
    public void incrementLevel(UUID id) {
        verifyId(id).setLevel(verifyId(id).getLevel() + 1);
    }

    /***
     *This section implements character behaviors in relation to their inventory
     */

    /*
     * Checks if the item is in the character's inventory and then returns true if it is.
     * Ensures that there are no issues when controller class calls a UsageDelegate
     * */
    public boolean hasItem(UUID id, Item item) {
        return verifyId(id).getInventory().contains(item);
    }

    /*
     * Checks if item is in inventory, then moves it to the first index at which item would be used
     * Returns True if item successfully selected, false if not
     * */
    public boolean swapSelectedItem(UUID id, Item item) {
        if (verifyId(id).getInventory().contains(item)) {
            Collections.swap(verifyId(id).getInventory(), 0, verifyId(id).getInventory().indexOf(item));
            return true;
        }
        return false;
    }

    /*
     * Adds item to the inventory
     * */
    public void addInventoryItem(UUID id, Item item) {
        verifyId(id).getInventory().add(item);
    }

    /*
     * Checks if item is in inventory, then removes if it is
     * Returns True if item successfully removed, false if not
     * */
    public boolean removeInventoryItem(UUID id, Item item) {
        if (verifyId(id).getInventory().contains(item)) {
            verifyId(id).getInventory().remove(item);
            return true;
        }
        return false;
    }

    public ArrayList<Item> getInventory(UUID id) {
        return verifyId(id).getInventory();
    }

    /*
     * Returns the worldEntity cast as a GameCharacter if the entity is an instance
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
