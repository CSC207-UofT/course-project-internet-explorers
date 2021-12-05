package level;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import core.levels.LevelLoader;
import core.worldEntities.types.characters.Character;
import core.levels.LevelManager;
import core.levels.LevelState;
import core.worldEntities.Spawner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static core.worldEntities.DemoSpawners.createEnemySpawner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLevel {

    LevelLoader levelLoader;
    LevelManager levelManager;

    @BeforeEach
    public void setup() {
        levelLoader = new LevelLoader();
        levelManager = new LevelManager(levelLoader.getLevel1());
    }

    @Test
    void testLevelLoader(){
        assertEquals("Level One", levelLoader.getLevel1());
    }

    @Test
    void testSavedToActive(){
        levelManager.createSaveLevel();
        //get the saved level from a file
        levelManager.updateLevel();
        //there is a method that updates the current level to the save
        assertEquals(levelLoader.saveLevel.getEnemySpawns(), levelLoader.activeLevel.getTotalEnemy());
    }

    @Test
    void testSerialization(){
        //<some code about serialization>
        assertEquals(levelLoader.saveLevel.getTime(), levelLoader.activeLevel.getTime());
    }

    @AfterEach
    public void teardown() {
        levelLoader.dispose();
        levelManager.dispose();
    }

}
