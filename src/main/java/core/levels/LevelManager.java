package core.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import core.world.EntitySpawner;
import core.world.WorldManager;
import java.util.List;

/**
 * Use-Case class for LevelState.
 * Implements methods for rendering level and advancing the level's World.
 */
public class LevelManager {

    private final LevelState level;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final WorldManager worldManager;
    private final SpriteBatch batch;
    private float spawnTime;

    public LevelManager(LevelState level) {
        this.level = level;
        map = new TmxMapLoader().load(level.getMapPath());
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, level.getUnitScale());
        this.worldManager = new WorldManager(level.world);
        this.batch = new SpriteBatch();
        this.spawnTime = 15;
    }

    /**
     * Step physics simulation of World
     * Elapse time in World
     * Spawn enemies in World
     *
     * Above actions only performed if level isn't in a paused state
     *
     * @param dt time delta to simulate (seconds) (capped at .5 in case computer is too slow)
     */
    public void step(float dt) {
        // If level isn't paused, perform step, time elapsing, spawning
        if (!level.getLevelPaused()) {
            // Stepping physics simulation
            worldManager.step(dt);

            // Elapsing time in world
            level.setCurrentTime(level.getCurrentTime() + dt);

            // Spawning enemies in world
            List<EntitySpawner> enemies = level.getEnemySpawns();

            if (enemies.isEmpty()) {
                // If all enemies have been spawned, check if game is won or not
                if (checkWin()) {
                    System.out.println("Level WIN!");
                }
            } else {
                // Spawn enemy every 15 seconds
                if (level.getCurrentTime() >= spawnTime) {
                    EntitySpawner enemy = enemies.remove(0);
                    enemy.spawn();
                    level.setScore(level.getScore() + 1);
                    spawnTime += 15;
                }
            }
        }
        // If level is paused, keep currentTime at same time
        else {
            level.setCurrentTime(level.getCurrentTime());
        }
    }

    /**
     * Checking win condition
     * level is won if 100 seconds have passed
     *
     * @return whether win condition has been met or not
     */
    public boolean checkWin() {
        return level.getCurrentTime() >= 100;
    }

    public void renderMap(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void renderWorld(OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldManager.draw(batch);
        batch.end();
    }

    public float getUnitScale() {
        return level.getUnitScale();
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public void dispose() {
        map.dispose();
    }

    public void pause() {
        level.setLevelPaused(true);
    }

    public void resume() {
        level.setLevelPaused(false);
    }
}
