package core.characters;

import com.badlogic.gdx.math.Vector2;
import core.InventorySystem.*;
import core.world.WorldEntityManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class CharacterManager {

    /*
     * Use case class that handles updating instances of GameCharacter based on inputs from the InputHandler
     * @param characterEntities: Hashmap storing characters as values with their UUID as keys
     * */

    public HashMap<UUID, GameCharacter> characterEntities;
    private final WorldEntityManager entityManager;

    public CharacterManager(WorldEntityManager entityManager) {
        this.characterEntities = new HashMap<>();
        this.entityManager = entityManager;
    }

    /**
     * Adds a WorldEntity in the entityManager to this CharacterManager.
     *
     * The WorldEntity must be already added to the entityManager and must be a subclass of GameCharacter.
     */
    public void addCharacter(UUID id) {
        if (entityManager.getEntity(id) instanceof GameCharacter character) {
            this.characterEntities.put(character.id, character);
        }
        throw new RuntimeException("Couldn't find a GameCharacter with the specified UUID: " + id);
    }

    // ben this is why we need a simple ControlState class this method signature is gonna get Huge
    private void acceptInput(UUID id, boolean up, boolean down, boolean left, boolean right, boolean use) {
        int dx = 0;
        dx += right ? 1 : 0;
        dx -= left ? 1 : 0;
        int dy = 0;
        dy += up ? 1 : 0;
        dy -= down ? 1 : 0;

        // m/s
        float speed = 10;
        entityManager.setLinearVelocity(id, new Vector2(dx, dy).nor().scl(speed));

        if (use) {
            WeaponUsageDelegate usageDelegate = new WeaponUsageDelegate(id);
            usageDelegate.use((Weapon) this.characterEntities.get(id).getInventory().get(0));
        }
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

        if (verifyId(id)) {
            this.characterEntities.get(id).setVelocity(new Vector2(dx, dy));
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
