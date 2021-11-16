package core.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.characters.GameCharacter;
import core.world.Spawner;

import java.io.Serializable;
import java.util.List;

/**
    Entity class that stores information of the level
 */
public class LevelState implements Serializable {

    private final String mapPath;
    protected World world;
    protected boolean levelPaused;
    private boolean levelFinished;
    protected transient float currentTime;
    protected int score;
    protected List<Spawner<GameCharacter>> enemySpawns;
    private float spawnTime;


    // unitScale measured in m/px
    // represents in-game size of map tiles
    // current conventions
    //  * 1 map tile is 1m by 1m
    //  * tiles are 32px by 32px
    private float unitScale;

    protected LevelState(String mapPath) {
        this.world = new World(new Vector2(0, 0), true);
        this.mapPath = mapPath;
        this.currentTime = 0;
        this.levelPaused = false;
        this.spawnTime = 15;
        this.levelFinished = false;
    }

    protected void setUnitScale(float unitScale) {
        this.unitScale = unitScale;
    }

    public float getUnitScale() {
        return this.unitScale;
    }

    public String getMapPath() {
        return mapPath;
    }

    public boolean getLevelPaused() {
        return levelPaused;
    }

    public void setLevelPaused(boolean set) {
        this.levelPaused = set;
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

    public float getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(float spawnTime) {
        this.spawnTime = spawnTime;
    }

    public boolean isLevelFinished() {
        return levelFinished;
    }

    public void finishedLevel(){
        levelFinished = true;
    }

    public List<Spawner<GameCharacter>> getEnemySpawns() {
        return enemySpawns;
    }

    public void setEnemySpawns(List<Spawner<GameCharacter>> enemySpawns) {
        this.enemySpawns = enemySpawns;
    }
}
