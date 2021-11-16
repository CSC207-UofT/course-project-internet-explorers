package core.levels;

import core.characters.GameCharacter;
import core.world.Spawner;

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

        LevelState lvl = new LevelState("maps/demo.tmx");
        lvl.setEnemySpawns(createEnemyList(5));
        lvl.setUnitScale(1 / 64f);

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

        float currentTime = (float) objectInputStream.readObject();

        LevelState lvl = new LevelState("maps/demo.tmx");
        lvl.setEnemySpawns(createEnemyList(5));
        lvl.setUnitScale(1 / 64f);

        // take current time and adjust enemyList
        lvl.setCurrentTime(currentTime);
        double numEnemies = Math.floor(lvl.getCurrentTime() / lvl.getSpawnTime());
        for (int i = 0; i < numEnemies; i++) {
            List<Spawner<GameCharacter>> enemiesUpdated = lvl.getEnemySpawns();
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
    private static List<Spawner<GameCharacter>> createEnemyList(int numOfEnemies) {
        List<Spawner<GameCharacter>> enemies = new ArrayList<>();
        for (int i = 0; i < numOfEnemies; i++) {
            Spawner<GameCharacter> enemySpawner = createEnemySpawner();
            enemySpawner.addSpawnCallback(character -> character.setTeam("enemy"));
            enemies.add(enemySpawner);
        }
        return enemies;
    }
}
