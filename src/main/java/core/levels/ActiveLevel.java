package core.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.worldEntities.Spawner;
import core.worldEntities.types.characters.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class that stores information of the level once the game has been started
 *
 * Child Class of LevelState
 */

public class ActiveLevel extends LevelState {

    protected World world;
    private final TiledMap map;
    protected List<Spawner<Character>> enemySpawns;
    protected boolean levelPaused;
    private boolean levelFinished;

    public ActiveLevel(float currentTime, int score, float spawnInterval, float nextSpawnTime, String MapPath) {
        this.setUnitScale(1 / 64f);
        this.currentTime = currentTime;
        this.score = score;
        this.spawnInterval = spawnInterval;
        this.nextSpawnTime = nextSpawnTime;
        this.map = new TmxMapLoader().load(MapPath);
        this.world = new World(new Vector2(0, 0), true);
        this.levelPaused = false;
        this.levelFinished = false;
        this.enemySpawns = new ArrayList<>();
    }

    public boolean isLevelFinished() { return levelFinished; }

    public void finishedLevel() { levelFinished = true; }

    public boolean getLevelPaused() { return levelPaused; }

    public void setLevelPaused(boolean set) { this.levelPaused = set; }

    public List<Spawner<Character>> getEnemySpawns() { return enemySpawns; }

    public void setEnemySpawns(List<Spawner<Character>> enemySpawns){ this.enemySpawns = enemySpawns; }

    public TiledMap getMap() { return this.map; }

    public World getWorld() { return this.world;}
}
