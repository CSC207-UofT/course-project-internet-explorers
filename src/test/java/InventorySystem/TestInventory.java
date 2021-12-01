package InventorySystem;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.inventory.Item;
import core.inventory.Weapon;
import core.inventory.items.*;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.GameCharacter;
import core.worldEntities.types.characters.CharacterManager;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestInventory {

    World world;
    CharacterManager characterManager;

    GameCharacter test_player;

    @BeforeAll
    static void makeApp() {
        new LwjglApplication(new ApplicationAdapter() {}, new LwjglApplicationConfiguration());
    }

    @BeforeEach
    public void setup() {
        world = new World(new Vector2(0, 0), true);
        WorldEntityManager entityManager = new WorldEntityManager(world);
        characterManager = new CharacterManager(entityManager);

        test_player = new GameCharacter();
    }

    @AfterEach
    public void teardown() {
        world.dispose();
    }

    public static void main(String[] args) {
        System.out.println(Gdx.files.getLocalStoragePath());
    }

    @Test
    void testDamage() {
        Item sword = new Sword(1);
        Item dagger = new Dagger(2);
        test_player.getInventory().add(sword);
        test_player.getInventory().add(dagger);
        Weapon weapon = (Weapon) test_player.getInventory().get(0);
        assertEquals(3, weapon.getDamage());
    }

    @Test
    void testInventory() {
        Item sword = new Sword(1);
        Item dagger = new Dagger(2);
        test_player.getInventory().add(sword);
        test_player.getInventory().add(dagger);

        ArrayList<Item> inventory = characterManager.openInventory(test_player.id);
        ArrayList<Item> comparison = new ArrayList<>();
        comparison.add(sword);
        comparison.add(dagger);
        assertEquals(comparison, inventory);
    }

    @Test
    void testSelect() {
        Item sword = new Sword(1);
        Item dagger = new Dagger(2);
        test_player.getInventory().add(sword);
        test_player.getInventory().add(dagger);
        assertTrue(characterManager.selectItem(test_player.id, test_player.getInventory().get(test_player.getInventory().indexOf(sword))));
    }
}
