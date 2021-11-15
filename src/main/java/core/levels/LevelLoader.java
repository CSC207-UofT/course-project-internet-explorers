package core.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.world.Spawner;
import core.world.WorldEntityWithSprite;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import static core.world.DemoSpawners.createEnemySpawner;

public class LevelLoader {

    /**
     * Load a new LevelState
     * @return LevelState
     */
    public static LevelState getLevel1() {
        // Create enemy spawner list
        List<Spawner<WorldEntityWithSprite>> enemies = createEnemyList(5);

        LevelState lvl = new LevelState("maps/demo.tmx", enemies);
        lvl.setUnitScale(1 / 64f);
        lvl.world = new World(new Vector2(0, 0), true);

        // TODO: set up player spawner

        return lvl;
    }

    /**
     * If savedState file exists, load LevelState given saved information
     * @return LevelState
     * @throws IOException relating to savedState.txt
     * @throws ClassNotFoundException relating to reading objects in
     */
    public static LevelState loadState() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("savedState.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        float currentTime = (float)objectInputStream.readObject();

        List<Spawner<WorldEntityWithSprite>> enemies = createEnemyList(5);
        LevelState lvl = new LevelState("maps/demo.tmx", enemies);
        lvl.setUnitScale(1 / 64f);
        lvl.world = new World(new Vector2(0, 0), true);

        // take current time and adjust enemyList
        lvl.setCurrentTime(currentTime);
        double numEnemies = Math.floor(lvl.getCurrentTime() / lvl.getSpawnTime());
        for (int i = 0; i < numEnemies; i++) {
            List<Spawner<WorldEntityWithSprite>> enemiesUpdated = lvl.getEnemySpawns();
            enemiesUpdated.remove(0);
            lvl.setEnemySpawns(enemiesUpdated);
        }

        return lvl;
    }

    /**
     * Creates list of enemies to be spawned in level
     * @param numOfEnemies wanted to be spawned in this level
     * @return enemies list
     */
    private static List<Spawner<WorldEntityWithSprite>> createEnemyList(int numOfEnemies){
        List<Spawner<WorldEntityWithSprite>> enemies = new ArrayList<>();
        for (int i = 0; i < numOfEnemies; i++){
            Spawner<WorldEntityWithSprite> enemySpawner = createEnemySpawner();
            enemies.add(enemySpawner);
        }
        return enemies;
    }
}
