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

    public HashMap<UUID, Character> characterEntities;
    private final WorldEntityManager entityManager;

    public CharacterManager(WorldEntityManager entityManager) {
        this.characterEntities = new HashMap<>();
        this.entityManager = entityManager;
    }

    /**
     * Adds a WorldEntity in the entityManager to this CharacterManager.
     * <p>
     * The WorldEntity must be already added to the entityManager and must be a subclass of GameCharacter.
     */
    public void addCharacter(UUID id, Class<? extends CharacterInputDevice> inputDeviceType) {
        if (entityManager.getEntity(id) instanceof Character character) {
            this.characterEntities.put(character.id, character);
            character.setInputDeviceType(inputDeviceType);
        } else {
            throw new RuntimeException("Couldn't find a GameCharacter with the specified UUID: " + id);
        }
    }

    public void addCharacter(UUID id) {
        addCharacter(id, CharacterInputDevice.class);
    }

    /**
     * Updates the GameCharacter's velocity to move character in direction specified by the given input (provided by InputController).
     * Also invokes item usage on the held item (per the input).
     */
    public void processInputs(float dt, Map<UUID, CharacterInput> inputs) {
        inputs.forEach((id, input) -> {
            // normalize input direction then scale by desired speed
            entityManager.setLinearVelocity(id, input.direction().nor().scl(Character.SPEED));

            // TODO separate movement/usage into separate methods (also consult with ben)
            if (input.using()) {
                WeaponUsageDelegate usageDelegate = new WeaponUsageDelegate(id);
                usageDelegate.use((Weapon) this.characterEntities.get(id).getInventory().get(0));
            }
        });
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
                Collections.swap(
                    this.characterEntities.get(id).getInventory(),
                    0,
                    this.characterEntities.get(id).getInventory().indexOf(item)
                );
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

    public ArrayList<Item> openInventory(UUID id) {
        /*
         * Returns inventory contents and displays them
         * Returns null if character id cannot be found
         * */
        if (verifyId(id)) {
            return this.characterEntities.get(id).getInventory();
        }
        return null;
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
