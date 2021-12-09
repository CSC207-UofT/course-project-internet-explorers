package inventorySystem;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import core.inventory.Item;
import core.inventory.Weapon;
import core.inventory.items.*;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.Character;
import core.worldEntities.types.characters.CharacterManager;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestInventory {

    CharacterManager characterManager;
    World world;

    Character test_player;
    ArrayList<Item> inv = new ArrayList<>();
    Weapon sword = new Sword(1);
    Item dagger = new Dagger(2);

    @BeforeEach
    public void setup() {
        world = new World(new Vector2(), true);
        WorldEntityManager entityManager = new WorldEntityManager(world);
        characterManager = new CharacterManager(entityManager);

        test_player = entityManager.createEntity(Character.class, new BodyDef());
        inv.add(sword);
        inv.add(dagger);
        test_player.setInventory(inv);
    }

    @AfterEach
    void teardown() {
        world.dispose();
    }

    @Test
    void testDamage() {
        Weapon weapon = (Weapon) test_player.getInventory().get(0);
        assertEquals(sword.getDamage(), weapon.getDamage());
    }

    @Test
    void testSelect() {
        assertTrue(
            characterManager.swapSelectedItem(
                test_player.getId(),
                test_player.getInventory().get(test_player.getInventory().indexOf(sword))
            )
        );
    }

    @Test
    void testAdd() {
        Item sword1 = new Sword(3);
        characterManager.addInventoryItem(test_player.getId(), sword1);
        assertTrue(
            characterManager.swapSelectedItem(
                test_player.getId(),
                test_player.getInventory().get(test_player.getInventory().indexOf(sword1))
            )
        );
    }

    @Test
    void testRemove() {
        characterManager.removeInventoryItem(test_player.getId(), sword);
        assertFalse(characterManager.swapSelectedItem(test_player.getId(), sword));
    }

    @Test
    void testSword() {
        Sword sword2 = new Sword(3);
        assertNotNull(sword2.getID());
        assertEquals(3, sword2.getLevel());
        assertEquals(9, sword2.getDamage());
        assertEquals(6, sword2.getRange());
    }

    @Test
    void testDagger() {
        Dagger dagger1 = new Dagger(3);
        assertNotNull(dagger.getID());
        assertEquals(3, dagger1.getLevel());
        assertEquals(6, dagger1.getDamage());
        assertEquals(3, dagger1.getRange());
    }
    //TODO: Add a test for using the item
}
