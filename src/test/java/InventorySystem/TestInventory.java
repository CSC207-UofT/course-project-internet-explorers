package InventorySystem;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import core.inventory.Item;
import core.inventory.ItemManager;
import core.inventory.Weapon;
import core.inventory.items.*;
import core.levels.LevelManager;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.Character;
import core.worldEntities.types.characters.CharacterManager;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestInventory {

    World world;
    CharacterManager characterManager;

    Character test_player;
    ArrayList<Item> inv = new ArrayList<>();
    Weapon sword = new Sword(1);
    Item dagger = new Dagger(2);

    @BeforeAll
    static void makeApp() {
        new LwjglApplication(new ApplicationAdapter() {}, new LwjglApplicationConfiguration());
    }

    @BeforeEach
    public void setup() {
        world = new World(new Vector2(0, 0), true);
        LevelManager levelManager = new LevelManager();
        levelManager.initializeEmptyLevel();
        characterManager = new CharacterManager(levelManager, new ItemManager(levelManager));

        test_player = levelManager.getEntityManager().createEntity(Character.class, new BodyDef());
        inv.add(sword);
        inv.add(dagger);
        test_player.setInventory(inv);
    }

    @AfterEach
    public void teardown() {
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
}
