package core.levels;

import core.config.Config;
import core.worldEntities.WorldEntityManager;
import java.io.*;

/**
 * Use-case class to manage serialization of levels
 *
 */
public class LevelLoader {

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
                case "Level 2" -> new SavedLevel("maps/demo.tmx", 7, 10);
                case "Level 3" -> new SavedLevel("maps/demo.tmx", 10, 5);
                default -> new SavedLevel("maps/demo.tmx", 5, 15);
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
            "maps/demo.tmx",
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
