package core.levels;

import java.util.ArrayList;
import java.util.Arrays;

public class SavedLevel extends LevelState {

    private final String mapPath;
    private int totalSpawns;
    private ArrayList<Float> playerPosition;
    private ArrayList<ArrayList<Float>> enemyPositions;
    private ArrayList<ArrayList<Float>> defenderPositions;

    public SavedLevel(String mapPath, int totalSpawns, float spawnInterval) {
        this.currentTime = 0;
        this.score = 0;
        this.spawnInterval = spawnInterval;
        this.mapPath = mapPath;
        this.totalSpawns = totalSpawns;
        this.playerPosition = new ArrayList<Float>(Arrays.asList(2F,2F));
        this.enemyPositions = new ArrayList<ArrayList<Float>>();
        this.defenderPositions = new ArrayList<ArrayList<Float>>();

    }

    public SavedLevel(float time, int score, float spawnInterval, String mapPath, int totalSpawns,
                      ArrayList<Float> playerPosition, ArrayList<ArrayList<Float>> enemyPositions,
                      ArrayList<ArrayList<Float>> defenderPositions){
        this.currentTime = time;
        this.score = score;
        this.spawnInterval = spawnInterval;
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
