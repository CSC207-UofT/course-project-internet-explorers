package core.levels;

import core.config.Config;
import core.worldEntities.WorldEntityManager;
import java.io.*;

/**
 * Use-case class to manage serialization of levels
 *
 */
public class LevelLoader {

    public static final String DEMO_MAP_PATH = "maps/demo.tmx";

    public static final String LEVEL_2_NAME = "Level 2";
    public static final int DEFAULT_TOTAL_SPAWNS = 5;
    public static final int LEVEL_2_TOTAL_SPAWNS = 7;
    public static final int LEVEL_3_TOTAL_SPAWNS = 10;

    public static final String LEVEL_3_NAME = "Level 3";
    public static final int DEFAULT_SPAWN_INTERVAL = 15;
    public static final int LEVEL_2_SPAWN_INTERVAL = 10;
    public static final int LEVEL_3_SPAWN_INTERVAL = 5;

    // TODO map path

    /**
     * If savedState file exists, load SavedLevel given saved information
     * If no savedState file exists, load SavedLevel given default information for user chosen
     * level difficulty
     *
     * @return SavedLevel to load into game
     */
    public static SavedLevel loadState(String fileName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName + ".txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (SavedLevel) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            String selectedLevel = LevelManager.selectedLevel.get();

            return switch (selectedLevel) {
                case LEVEL_2_NAME -> new SavedLevel(DEMO_MAP_PATH, LEVEL_2_TOTAL_SPAWNS, LEVEL_2_SPAWN_INTERVAL);
                case LEVEL_3_NAME -> new SavedLevel(DEMO_MAP_PATH, LEVEL_3_TOTAL_SPAWNS, LEVEL_3_SPAWN_INTERVAL);
                default -> new SavedLevel(DEMO_MAP_PATH, DEFAULT_TOTAL_SPAWNS, DEFAULT_SPAWN_INTERVAL);
            };
        }
    }

    /**
     * Save current ActiveLevel into a SavedLevel
     *    * assign SavedLevel.totalSpawns to length of ActiveLevel.enemySpawns
     *    * assign SavedLevel.currentTime to ActiveLevel.currentTime
     *    * pass through current user player position
     *    * pass through list of positions of current enemies on the map
     *    * pass through list of positions of current defenders on the map
     *
     * @throws IOException            relating to savedState.txt
     */
    public static void saveState(String fileName, LevelManager levelManager) throws IOException {
        ActiveLevel level = levelManager.getActiveLevel();
        WorldEntityManager entityManager = levelManager.getEntityManager();

        FileOutputStream fileOutputStream = new FileOutputStream(fileName + ".txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        SavedLevel savedLevel = new SavedLevel(
            level.getCurrentTime(),
            level.getScore(),
            level.getSpawnInterval(),
            level.getNextSpawnTime(),
            DEMO_MAP_PATH,
            level.getEnemySpawns().size(),
            entityManager.getPlayerPosition(),
            entityManager.getEnemyPositions(),
            entityManager.getDefenderPositions()
        );

        objectOutputStream.writeObject(savedLevel);

        objectOutputStream.flush();
        objectOutputStream.close();
    }

    /**
     * Delete the save file for the given level
     * @param fileName given level to be deleted
     */
    public static void DeleteLevel(String fileName) {
        File file = new File(fileName + ".txt");
        if (!file.delete()) {
            System.err.println("Failed to delete save file " + fileName);
        }
    }
}
