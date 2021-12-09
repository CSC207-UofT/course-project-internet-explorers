package level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import core.levels.LevelLoader;
import core.levels.LevelManager;
import core.levels.SavedLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLevel {

    @BeforeEach
    public void setup() {
        Gdx.files = mock(Files.class);
        LevelManager levelManager = new LevelManager();
        SavedLevel savedLevel = new SavedLevel(LevelLoader.DEMO_MAP_PATH, 5, 15);
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
    //    @Test
    //    void testSavedToActive(){
    //        levelManager.createSaveLevel();
    //        get the saved level from a file
    //        levelManager.updateLevel();
    //        there is a method that updates the current level to the save
    //        assertEquals(levelLoader.saveLevel.getEnemySpawns(), levelLoader.activeLevel.getTotalEnemy());
    //    }

    //    @Test
    //    void testSerialization(){
    //        //<some code about serialization>
    ////        assertEquals(levelLoader.saveLevel.getTime(), levelLoader.activeLevel.getTime());
    //    }
}
