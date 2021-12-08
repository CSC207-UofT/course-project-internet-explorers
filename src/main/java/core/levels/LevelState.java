package core.levels;

import java.io.Serializable;

/**
 * Entity class that stores information of the level that is common to both saved levels and
 * mid-game active levels of the game.
 *
 * Parent Class of classes SavedLevel, ActiveLevel
 */
public class LevelState implements Serializable {

    protected float currentTime;
    protected int score;
    protected float spawnInterval;
    // unitScale measured in m/px
    // represents in-game size of map tiles
    // current conventions
    //  * 1 map tile is 1 m by 1 m
    //  * tiles are 32px by 32px
    private float unitScale;

    public float getUnitScale() {
        return this.unitScale;
    }

    protected void setUnitScale(float unitScale) {
        this.unitScale = unitScale;
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
