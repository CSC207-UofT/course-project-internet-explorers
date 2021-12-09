package characterTests;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import core.inventory.ItemManager;
import core.inventory.items.Sword;
import core.levels.LevelManager;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.Character;
import core.worldEntities.types.characters.CharacterManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCharacterManager {

    CharacterManager characterManager;
    World world;

    Character character;
    Sword sword;

    @Before
    public void setup() {
        world = new World(new Vector2(0, 0), true);
        LevelManager levelManager = new LevelManager();
        ItemManager itemManager = new ItemManager(levelManager);
        levelManager.initializeEmptyLevel();
        WorldEntityManager entityManager = levelManager.getEntityManager();
        character = entityManager.createEntity(Character.class, new BodyDef());
        character.setHealth(100);
        sword = itemManager.createItem(Sword.class);

        characterManager = new CharacterManager(levelManager, new ItemManager(levelManager));
    }

    @AfterEach
    void teardown() {
        world.dispose();
    }

    @Test
    public void testUpdateLevel() {
        characterManager.incrementLevel(character.getId());
        assertEquals(character.getLevel(), 1);
    }

    @Test
    public void testAddInventory() {
        characterManager.addInventoryItem(character.getId(), sword.getId());
        assertEquals(character.getInventory().size(), 1);
    }

    @Test
    public void testCanUseItem() {
        characterManager.addInventoryItem(character.getId(), sword.getId());
        assertTrue(characterManager.hasItem(character.getId(), sword.getId()));
    }

    @Test
    public void testRemoveInventory() {
        characterManager.addInventoryItem(character.getId(), sword.getId());
        assertTrue(characterManager.removeInventoryItem(character.getId(), sword.getId()));
    }
}
