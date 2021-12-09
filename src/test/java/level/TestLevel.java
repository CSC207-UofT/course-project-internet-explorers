package level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import core.levels.LevelLoader;
import core.levels.LevelManager;
import core.levels.SavedLevel;
import core.worldEntities.DemoSpawners;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestLevel {

    LevelManager levelManager;

    @BeforeEach
    public void setup() {
        Gdx.files = mock(Files.class);
        TextureAtlas textureAtlas = mock(TextureAtlas.class);
        when(textureAtlas.createSprite(DemoSpawners.DEMO_PLAYER_SPRITE_NAME)).thenReturn(mock(Sprite.class));
        when(textureAtlas.createSprite(DemoSpawners.DEMO_SPIKE_SPRITE_NAME)).thenReturn(mock(Sprite.class));
        when(textureAtlas.createSprite(DemoSpawners.DEMO_ENEMY_SPRITE_NAME)).thenReturn(mock(Sprite.class));
        when(textureAtlas.createSprite(DemoSpawners.DEMO_DEFENSE_SPRITE_NAME)).thenReturn(mock(Sprite.class));

        levelManager = new LevelManager(mock(TmxMapLoader.class), new DemoSpawners(textureAtlas));
        SavedLevel savedLevel = new SavedLevel(LevelLoader.DEMO_MAP_PATH, LevelLoader.DEFAULT_TOTAL_SPAWNS,
                                               LevelLoader.DEFAULT_SPAWN_INTERVAL);
        levelManager.initializeLevel(savedLevel);
    }

    @Test
    void testLevelLoader() {
        SavedLevel defaultLevel = LevelLoader.loadState("");
        SavedLevel levelTwo = LevelLoader.loadState(LevelLoader.LEVEL_2_NAME);
        SavedLevel levelThree = LevelLoader.loadState(LevelLoader.LEVEL_3_NAME);

        assertEquals(LevelLoader.DEFAULT_TOTAL_SPAWNS, defaultLevel.getTotalSpawns());
        assertEquals(LevelLoader.DEFAULT_SPAWN_INTERVAL, defaultLevel.getSpawnInterval());
        assertEquals(LevelLoader.LEVEL_2_TOTAL_SPAWNS, levelTwo.getTotalSpawns());
        assertEquals(LevelLoader.LEVEL_2_SPAWN_INTERVAL, levelTwo.getSpawnInterval());
        assertEquals(LevelLoader.LEVEL_3_TOTAL_SPAWNS, levelThree.getTotalSpawns());
        assertEquals(LevelLoader.LEVEL_3_SPAWN_INTERVAL, levelThree.getSpawnInterval());
    }

    @Test
    void testSavedToActive(){
        assertEquals(LevelLoader.DEFAULT_TOTAL_SPAWNS, levelManager.getActiveLevel().getEnemySpawns().size());
    }

    @Test
    void testSerialization() throws IOException {
        float testTime = 10;
        levelManager.getActiveLevel().setCurrentTime(testTime);
        LevelLoader.saveState("serialization-test", levelManager);
        SavedLevel serializedLevel = LevelLoader.loadState("serialization-test");

        assertEquals(testTime, serializedLevel.getCurrentTime());
    }
}
