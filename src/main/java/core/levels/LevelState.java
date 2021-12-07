package core.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.worldEntities.Spawner;
import core.worldEntities.types.characters.Character;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entity class that stores information of the level
 */
public class LevelState implements Serializable {

/*
    private record DifficultySettings(float spawnInterval, int totalSpawns) {};

    public Map<String, DifficultySettings> getDifficultySettingsMap() {
        HashMap<String, DifficultySettings> difficultySettingsMap = new HashMap<>();
        difficultySettingsMap.put("1", new DifficultySettings(15f, 5));
        difficultySettingsMap.put("2", new DifficultySettings(10f, 5));
        difficultySettingsMap.put("3", new DifficultySettings(10f, 10));
        difficultySettingsMap.put("4", new DifficultySettings(8f, 20));
    }

    // DiffSettings -> initialized levelstate

    // export diffSettings to file

    /*


 */

    // LevelState
    protected float currentTime;
    protected int score;
    protected float spawnInterval;
    // unitScale measured in m/px
    // represents in-game size of map tiles
    // current conventions
    //  * 1 map tile is 1m by 1m
    //  * tiles are 32px by 32px
    private float unitScale;

    // SavedLevel extends LevelState implements Serializable
//    private final String mapPath;
//    private int totalSpawns;

    // ActiveLevel extends LevelState
//    protected World world;
//    private TiledMap map;
//    protected List<Spawner<Character>> enemySpawns;
//    protected boolean levelPaused;
//    private boolean levelFinished;

    protected void setUnitScale(float unitScale) {
        this.unitScale = unitScale;
    }

    public float getUnitScale() {
        return this.unitScale;
    }

    public float getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(float currentTime) {
        this.currentTime = currentTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public float getSpawnInterval() {
        return spawnInterval;
    }

    public void setSpawnInterval(float spawnInterval) {
        this.spawnInterval = spawnInterval;
    }
}
