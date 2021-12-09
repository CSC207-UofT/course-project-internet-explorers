package inventorySystem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import core.inventory.Item;
import core.inventory.ItemManager;
import core.inventory.Weapon;
import core.inventory.items.*;
import core.levels.LevelLoader;
import core.levels.LevelManager;
import core.levels.SavedLevel;
import core.worldEntities.SpawnerFactory;
import core.worldEntities.types.characters.Character;
import core.worldEntities.types.characters.CharacterManager;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestInventory {

    CharacterManager characterManager;
    World world;

    Character test_player;
    final ArrayList<Item> inv = new ArrayList<>();
    Sword sword;
    Dagger dagger;
    Defender defender;
    ItemManager itemManager;
    LevelManager levelManager;

    @BeforeEach
    public void setup() {
        world = new World(new Vector2(0, 0), true);

        Gdx.files = mock(Files.class);
        TextureAtlas textureAtlas = mock(TextureAtlas.class);
        when(textureAtlas.createSprite(SpawnerFactory.DEMO_PLAYER_SPRITE_NAME)).thenReturn(mock(Sprite.class));
        when(textureAtlas.createSprite(SpawnerFactory.DEMO_SPIKE_SPRITE_NAME)).thenReturn(mock(Sprite.class));
        when(textureAtlas.createSprite(SpawnerFactory.DEMO_ENEMY_SPRITE_NAME)).thenReturn(mock(Sprite.class));
        when(textureAtlas.createSprite(SpawnerFactory.DEMO_DEFENSE_SPRITE_NAME)).thenReturn(mock(Sprite.class));

        levelManager = new LevelManager(mock(TmxMapLoader.class), new SpawnerFactory(textureAtlas));
        this.itemManager = new ItemManager(levelManager);
        SavedLevel savedLevel = new SavedLevel(
            LevelLoader.DEMO_MAP_PATH,
            LevelLoader.DEFAULT_TOTAL_SPAWNS,
            LevelLoader.DEFAULT_SPAWN_INTERVAL
        );
        levelManager.initializeLevel(savedLevel);
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
        characterManager.addInventoryItem(test_player.getId(), sword.getId());
        assertTrue(characterManager.swapSelectedItem(test_player.getId(), sword.getId()));
    }

    @Test
    void testAdd() {
        characterManager.addInventoryItem(test_player.getId(), defender.getId());
        assertTrue(characterManager.swapSelectedItem(test_player.getId(), defender.getId()));
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
