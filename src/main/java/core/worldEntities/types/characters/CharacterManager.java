package core.worldEntities.types.characters;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.input.*;
import core.inventory.Item;
import core.inventory.ItemManager;
import core.levels.LevelManager;
import core.worldEntities.WorldEntityManager;
import java.util.*;
import java.util.function.Supplier;

public class CharacterManager {

    /*
     * Use case class that handles updating instances of GameCharacter based on inputs from the InputHandler
     * @param characterEntities: Hashmap storing characters as values with their UUID as keys
     * */

    private final WorldEntityManager entityManager;
    private final LevelManager levelManager;
    private ItemManager itemManager;

    public CharacterManager(LevelManager levelManager, ItemManager itemManager) {
        this.levelManager = levelManager;
        // TODO clean this up; always getEntityManager instead of storing
        this.entityManager = levelManager.getEntityManager();
        this.itemManager = itemManager;
    }

    /**
     * Updates the GameCharacter's velocity to move character in direction specified by the given input
     * Also invokes item usage on the held item (per the input).
     */
    private void handleCharacterInput(UUID id, Character.Input input) {
        // normalize input direction then scale by desired speed
        entityManager.getEntity(id).setLinearVelocity(input.direction().nor().scl(Character.SPEED));

        if (input.using()) {
            useSelectedItem(id);
        }
    }

    public void addCharacterInputMapping(InputManager inputManager, UUID id, Supplier<Character.Input> inputSupplier) {
        inputManager.addInputMapping(new InputMapping<>(inputSupplier, input -> handleCharacterInput(id, input)));
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
    public boolean hasItem(UUID id, UUID itemId) {
        return verifyId(id).getInventory().contains(this.itemManager.get(itemId));
    }

    /*
     * Checks if item is in inventory, then moves it to the first index at which item would be used
     * Returns True if item successfully selected, false if not
     * */
    public boolean swapSelectedItem(UUID id, UUID itemId) {
        if (verifyId(id).getInventory().contains(this.itemManager.get(itemId))) {
            Collections.swap(verifyId(id).getInventory(), 0,
                             verifyId(id).getInventory().indexOf(this.itemManager.get(itemId)));
            return true;
        }
        return false;
    }

    /**
     * gets the item currently selected by the specified character
     */
    private Item getSelectedItem(UUID id) {
        return verifyId(id).getInventory().get(0);
    }

    private void useSelectedItem(UUID id) {
        Item item = getSelectedItem(id);
        itemManager.use(id, item.getId());
    }

    /*
     * Adds item to the inventory
     * */
    public void addInventoryItem(UUID id, UUID itemId) {
        verifyId(id).getInventory().add(this.itemManager.get(itemId));
    }

    /*
     * Checks if item is in inventory, then removes if it is
     * Returns True if item successfully removed, false if not
     * */
    public boolean removeInventoryItem(UUID id, UUID itemId) {
        if (verifyId(id).getInventory().contains(this.itemManager.get(itemId))) {
            verifyId(id).getInventory().remove(this.itemManager.get(itemId));
            return true;
        }
        return false;
    }

    public ArrayList<Item> getInventory(UUID id) {
        return verifyId(id).getInventory();
    }

    public ImageButton createInventorySlot(UUID itemId, int index) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        if (index == 0) {
            style.up = new TextureRegionDrawable(this.itemManager.get(itemId).getSelectedTexture());
        } else {
            style.up = new TextureRegionDrawable(this.itemManager.get(itemId).getUnselectedTexture());
        }

        return new ImageButton(style);
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
