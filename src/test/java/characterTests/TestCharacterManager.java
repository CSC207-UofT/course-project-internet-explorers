package characterTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import core.inventory.ItemManager;
import core.inventory.items.Sword;
import core.levels.LevelManager;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.Character;
import core.worldEntities.types.characters.CharacterManager;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;

public class TestCharacterManager {

    /*
     * Tests the CharacterManager use case class
     * */World world;
    WorldEntityManager entityManager;
    CharacterManager cm;

    Character character;
    Sword sword;

    @Before
    public void setup() {
        world = new World(new Vector2(0, 0), true);
        LevelManager levelManager = new LevelManager();
        ItemManager itemManager = new ItemManager(levelManager);
        levelManager.initializeEmptyLevel();
        entityManager = levelManager.getEntityManager();
        character = entityManager.createEntity(Character.class, new BodyDef());

        character.setHealth(100);
        sword = itemManager.createItem(Sword.class);

        cm = new CharacterManager(levelManager, new ItemManager(levelManager));
    }

    @AfterEach
    public void teardown() {
        world.dispose();
    }

    @org.junit.Test
    public void testUpdateLevel() {
        cm.incrementLevel(character.getId());
        assertEquals(character.getLevel(), 1);
    }

    @org.junit.Test
    public void testAddInventory() {
        cm.addInventoryItem(character.getId(), sword.getId());
        assertEquals(character.getInventory().size(), 1);
    }

    @org.junit.Test
    public void testCanUseItem() {
        cm.addInventoryItem(character.getId(), sword.getId());
        assertTrue(cm.hasItem(character.getId(), sword.getId()));
    }

    @org.junit.Test
    public void testRemoveInventory() {
        cm.addInventoryItem(character.getId(), sword.getId());
        assertTrue(cm.removeInventoryItem(character.getId(), sword.getId()));
    }
}
