package level;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import core.config.Config;
import core.levels.LevelLoader;
import core.levels.SavedLevel;
import core.levels.LevelManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLevel {

    LevelManager levelManager;

    @BeforeEach
    public void setup() throws IOException, ClassNotFoundException {
        LevelManager levelManager = new LevelManager();
        SavedLevel savedLevel = new SavedLevel("maps/demo.tmx", 5, 15);
        levelManager.initializeLevel(savedLevel);
    }

    @Test
    void testLevelLoader() throws IOException, ClassNotFoundException {
        SavedLevel levelOne = LevelLoader.loadState("Level 1");
        SavedLevel levelTwo = LevelLoader.loadState("Level 2");
        SavedLevel levelThree = LevelLoader.loadState("Level 3");

        assertEquals(5, levelOne.getTotalSpawns());
        assertEquals(15, levelOne.getSpawnInterval());
        assertEquals(7, levelTwo.getTotalSpawns());
        assertEquals(10, levelTwo.getSpawnInterval());
        assertEquals(10, levelThree.getTotalSpawns());
        assertEquals(5, levelThree.getSpawnInterval());
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
