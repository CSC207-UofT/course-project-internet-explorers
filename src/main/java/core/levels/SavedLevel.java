package core.levels;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Entity class that stores information of the level that is unique to pre-saved states of the game
 *
 * Child Class of LevelState
 */

public class SavedLevel extends LevelState {

    private final String mapPath;
    private final int totalSpawns;
    private final ArrayList<Float> playerPosition;
    private final ArrayList<ArrayList<Float>> enemyPositions;
    private final ArrayList<ArrayList<Float>> defenderPositions;

    /**
     * Constructor for a SavedLevel that hasn't been played, the default settings for a level.
     *
     * @param mapPath default map path for level default setting
     * @param totalSpawns default number of enemy spawns for level default setting
     * @param spawnInterval default spawn interval for enemies for level default setting
     */
    public SavedLevel(String mapPath, int totalSpawns, float spawnInterval) {
        this.currentTime = 0;
        this.score = 0;
        this.spawnInterval = spawnInterval;
        this.nextSpawnTime = spawnInterval;
        this.mapPath = mapPath;
        this.totalSpawns = totalSpawns;
        this.playerPosition = new ArrayList<>(Arrays.asList(2F, 2F));
        this.enemyPositions = new ArrayList<>();
        this.defenderPositions = new ArrayList<>();
    }

    /**
     * Constructor for a SavedLevel that has been played, including saved gameplay information
     * in addition to default level settings.
     *
     * @param time time when user exited game
     * @param score score user had when exited game
     * @param spawnInterval spawn interval for level when user exited game
     * @param mapPath map for level when user exited game
     * @param totalSpawns count for enemies that have yet to be spawned when user exited game
     * @param playerPosition list that includes position that user character is at
     * @param enemyPositions list that includes positions of all enemies on the map
     * @param defenderPositions list that includes positions of all defenders on the map
     */
    public SavedLevel(float time, int score, float spawnInterval, String mapPath, int totalSpawns,
                      ArrayList<Float> playerPosition, ArrayList<ArrayList<Float>> enemyPositions,
                      ArrayList<ArrayList<Float>> defenderPositions){
        this.currentTime = time;
        this.score = score;
        this.spawnInterval = spawnInterval;
        this.nextSpawnTime = spawnInterval;
        this.mapPath = mapPath;
        this.totalSpawns = totalSpawns;
        this.playerPosition = playerPosition;
        this.enemyPositions = enemyPositions;
        this.defenderPositions = defenderPositions;
    }

    public String getMapPath() { return mapPath; }

    public Integer getTotalSpawns(){ return totalSpawns; }

    public ArrayList<Float> getPlayerPosition(){
        return playerPosition;
    }

    public ArrayList<ArrayList<Float>> getEnemyPositions(){
        return enemyPositions;
    }

    public ArrayList<ArrayList<Float>> getDefenderPositions(){
        return defenderPositions;
    }
}
