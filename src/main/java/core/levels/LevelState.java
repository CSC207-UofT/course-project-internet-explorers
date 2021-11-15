package core.levels;

import com.badlogic.gdx.physics.box2d.World;
import core.world.EntitySpawner;
import java.util.List;

/**
    Entity class that stores information of the level
 */
public class LevelState {

    private String mapPath;
    protected World world;
    protected boolean levelPaused;
    protected float currentTime;
    protected int score;
    protected List<EntitySpawner> enemySpawns;

    // unitScale measured in m/px
    // represents in-game size of map tiles
    // current conventions
    //  * 1 map tile is 1m by 1m
    //  * tiles are 32px by 32px
    private float unitScale;

    protected LevelState(String mapPath, List<EntitySpawner> enemies) {
        this.mapPath = mapPath;
        this.currentTime = 0;
        this.levelPaused = false;
        this.enemySpawns = enemies;
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

    public List<EntitySpawner> getEnemySpawns() {
        return enemySpawns;
    }

    public void setEnemySpawns(List<EntitySpawner> enemySpawns) {
        this.enemySpawns = enemySpawns;
    }
}
