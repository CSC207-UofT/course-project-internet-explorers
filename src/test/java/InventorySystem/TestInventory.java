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
    Sword sword;
    Dagger dagger;
    Defender defender;
    ItemManager itemManager;

    @BeforeAll
    static void makeApp() {
        new LwjglApplication(new ApplicationAdapter() {}, new LwjglApplicationConfiguration());
    }

    @BeforeEach
    public void setup() {
        world = new World(new Vector2(0, 0), true);
        LevelManager levelManager = new LevelManager();
        this.itemManager = new ItemManager(levelManager);
        levelManager.initializeEmptyLevel();
        characterManager = new CharacterManager(levelManager, new ItemManager(levelManager));

        test_player = levelManager.getEntityManager().createEntity(Character.class, new BodyDef());

        sword = itemManager.createItem(Sword.class);
        dagger = itemManager.createItem(Dagger.class);
        defender = itemManager.createItem(Defender.class);

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
        characterManager.addInventoryItem(test_player.getId(), sword.getId());
        assertTrue(
            characterManager.swapSelectedItem(
                test_player.getId(),
                sword.getId()
            )
        );
    }

    @Test
    void testAdd() {
        characterManager.addInventoryItem(test_player.getId(), defender.getId());
        assertTrue(
            characterManager.swapSelectedItem(
                test_player.getId(),
                defender.getId()
            )
        );
    }

    @Test
    void testRemove() {
        characterManager.removeInventoryItem(test_player.getId(), sword.getId());
        assertFalse(characterManager.swapSelectedItem(test_player.getId(), sword.getId()));
    }

    @Test
    void testCreate() {
        sword = itemManager.createItem(Sword.class);
        assertEquals(sword, itemManager.get(sword.getId()));
    }

}
