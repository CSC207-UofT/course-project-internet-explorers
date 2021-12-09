package characterTests;

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
import core.inventory.ItemManager;
import core.inventory.items.Sword;
import core.levels.LevelLoader;
import core.levels.LevelManager;
import core.levels.SavedLevel;
import core.worldEntities.SpawnerFactory;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.Character;
import core.worldEntities.types.characters.CharacterManager;
import org.junit.jupiter.api.*;

public class TestCharacterManager {

    CharacterManager characterManager;
    World world;

    Character character;
    Sword sword;

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
        ItemManager itemManager = new ItemManager(levelManager);
        SavedLevel savedLevel = new SavedLevel(LevelLoader.DEMO_MAP_PATH, LevelLoader.DEFAULT_TOTAL_SPAWNS,
                                               LevelLoader.DEFAULT_SPAWN_INTERVAL);
        levelManager.initializeLevel(savedLevel);
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
