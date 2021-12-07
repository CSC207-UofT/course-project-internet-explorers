package core.levels;

import static core.worldEntities.DemoSpawners.createEnemySpawner;

import core.worldEntities.Spawner;
import core.worldEntities.types.characters.Character;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {

    /**
     * save file contents:
     *
     */

    final int SELECTED_LEVEL = 1;
    
//    public static LevelState getSelectedLevel() {
        // load file for selected level
        
        // for now, use manual method
//        return getLevel1();
//    }
    
    /**
     * Load a new LevelState
     * @return LevelState
     */
//    public static LevelState getLevel1(){


//        // Initialize LevelState
//        LevelState lvl = commonSetup();
//        lvl.setLevelDifficulty("L1");

//        try {
//            LevelState lvl = loadState("LevelOne");
//            lvl.setLevelDifficulty("L1");

//            return lvl;
//        } catch (IOException | ClassNotFoundException e){
//            e.printStackTrace();
//            throw new RuntimeException("rawr");
//        }
//    }

    /**
     * Initiate level at given levelDifficulty
     *
     * @return LevelState with assigned levelDifficulty
     *
     */
//    public LevelState initializeLevel(String levelDifficulty){
//        LevelState lvl = commonSetup();
//        lvl.setLevelDifficulty(levelDifficulty);
//        return lvl;
//    }

    /**
     * If savedState file exists, load LevelState given saved information
     * This method will be used in phase 2
     *
     * @return LevelState
     * @throws IOException relating to savedState.txt
     * @throws ClassNotFoundException relating to reading objects in
     */
    public static SavedLevel loadState(String fileName) throws IOException, ClassNotFoundException {
        // Take file that has saved level state
        SavedLevel lvl = new SavedLevel("maps/demo.tmx", 5, 15);

//        FileInputStream fileInputStream = new FileInputStream(fileName + ".txt");
//        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

//        SavedLevel lvl = (SavedLevel) objectInputStream.readObject();

        // Initialize LevelState and assign enemy spawns
//        SavedLevel lvl = new SavedLevel()

        // Take current time and adjust enemyList
//        lvl.setCurrentTime(currentTime);

        return lvl;
    }

    /**
     * Return LevelState at its most simple setup
     *
     * @return Most basic setup of LevelState
     */
//    public static LevelState commonSetup(){
//        LevelState lvl = new LevelState("maps/demo.tmx");
//        lvl.setUnitScale(1 / 64f);
//        return lvl;
//    }

    public static void main(String[] args) throws IOException{
        FileOutputStream fileOutputStream = new FileOutputStream("LevelOne.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeFloat(0f);

        objectOutputStream.flush();
        objectOutputStream.close();
    }
}
