package characterTests;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import core.inventory.items.Sword;
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

    @BeforeEach
    void setup() {
        world = new World(new Vector2(), true);
        WorldEntityManager entityManager = new WorldEntityManager(world);
        characterManager = new CharacterManager(entityManager);

        character = entityManager.createEntity(Character.class, new BodyDef());
        character.setHealth(100);
        sword = new Sword(2);
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
        characterManager.addInventoryItem(character.getId(), sword);
        assertEquals(character.getInventory().size(), 1);
    }

    @Test
    public void testCanUseItem() {
        characterManager.addInventoryItem(character.getId(), sword);
        assertTrue(characterManager.hasItem(character.getId(), sword));
    }

    @Test
    public void testRemoveInventory() {
        characterManager.addInventoryItem(character.getId(), sword);
        assertTrue(characterManager.removeInventoryItem(character.getId(), sword));
    }
}
