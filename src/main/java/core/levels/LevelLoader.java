package core.levels;

import static core.worldEntities.DemoSpawners.createEnemySpawner;

import core.worldEntities.Spawner;
import core.worldEntities.types.characters.Character;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {

    /**
     * Load a new LevelState
     * @return LevelState
     */
    public static LevelState getLevel1() {
        // Initialize LevelState
        LevelState lvl = commonSetup();
        lvl.setLevelDifficulty("L1");

        return lvl;
    }

    /**
     * Initiate level at given levelDifficulty
     *
     * @return LevelState with assigned levelDifficulty
     *
     */
    public LevelState initializeLevel(String levelDifficulty){
        LevelState lvl = commonSetup();
        lvl.setLevelDifficulty(levelDifficulty);
        return lvl;
    }

    /**
     * If savedState file exists, load LevelState given saved information
     * This method will be used in phase 2
     *
     * @return LevelState
     * @throws IOException relating to savedState.txt
     * @throws ClassNotFoundException relating to reading objects in
     */
    public static LevelState loadState() throws IOException, ClassNotFoundException {
        // Take file that has saved level state
        FileInputStream fileInputStream = new FileInputStream("savedState.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        float currentTime = (float) objectInputStream.readObject();

        // Initialize LevelState and assign enemy spawns
        LevelState lvl = new LevelState("maps/demo.tmx");
        lvl.setUnitScale(1 / 64f);

        // Take current time and adjust enemyList
        lvl.setCurrentTime(currentTime);

        return lvl;
    }

    /**
     * Return LevelState at its most simple setup
     *
     * @return Most basic setup of LevelState
     */
    public static LevelState commonSetup(){
        LevelState lvl = new LevelState("maps/demo.tmx");
        lvl.setUnitScale(1 / 64f);
        return lvl;
    }
}
