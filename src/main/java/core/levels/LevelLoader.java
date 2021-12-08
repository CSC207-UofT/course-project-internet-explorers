package core.levels;

import core.config.Config;
import java.io.*;


public class LevelLoader {

    /**
     * If savedState file exists, load LevelState given saved information
     *
     * @return LevelState
     * @throws IOException relating to savedState.txt
     * @throws ClassNotFoundException relating to reading objects in
     */
    public static SavedLevel loadState(String fileName) throws IOException, ClassNotFoundException {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName + ".txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (SavedLevel) objectInputStream.readObject();

        } catch (IOException exception) {
            String selectedLevel = (String) Config.get("selected-level");

            return switch (selectedLevel) {
                case "Level 2" -> new SavedLevel("maps/demo.tmx", 7, 10);
                case "Level 3" -> new SavedLevel("maps/demo.tmx", 10, 5);
                default -> new SavedLevel("maps/demo.tmx", 5, 15);
            };
        }
    }

}
