package core.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.worldEntities.Spawner;
import core.worldEntities.types.characters.Character;

import java.util.ArrayList;
import java.util.List;

public class ActiveLevel extends LevelState {

    protected transient World world;
    private transient final TiledMap map;
    protected List<Spawner<Character>> enemySpawns;
    protected boolean levelPaused;
    private boolean levelFinished;

    public ActiveLevel(SavedLevel level) {
        this.setUnitScale(1 / 64f);
        this.currentTime = level.getCurrentTime();
        this.score = level.getScore();
        this.spawnInterval = level.getSpawnInterval();
        this.map = new TmxMapLoader().load(level.getMapPath());
        this.world = new World(new Vector2(0, 0), true);
        this.levelPaused = false;
        this.levelFinished = false;
        this.enemySpawns = new ArrayList<Spawner<Character>>();
    }

    public boolean isLevelFinished() { return levelFinished; }

    public void finishedLevel() { levelFinished = true; }

    public boolean getLevelPaused() { return levelPaused; }

    public void setLevelPaused(boolean set) { this.levelPaused = set; }

    public List<Spawner<Character>> getEnemySpawns() { return enemySpawns; }

    public void setEnemySpawns(List enemySpawns){ this.enemySpawns = enemySpawns; }

    public TiledMap getMap() { return this.map; }

    public World getWorld() { return this.world;}
}
