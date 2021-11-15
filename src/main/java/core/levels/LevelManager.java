package core.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import core.world.SpawnController;
import core.world.WorldEntity;
import core.world.WorldEntityManager;

/**
 * Use-Case class for LevelState.
 * Implements methods for rendering level and advancing the level's World.
 */
public class LevelManager {

    private final LevelState level;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final WorldEntityManager entityManager;
    private final SpriteBatch batch;

    public LevelManager(LevelState level) {
        this.level = level;
        map = new TmxMapLoader().load(level.getMapPath());
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, level.getUnitScale());
        this.entityManager = new WorldEntityManager(level.world);

        this.batch = new SpriteBatch();
    }

    /**
     * Steps the physics simulation of the level's World.
     *
     * @param dt time delta to simulate (seconds) (capped at .5 in case computer is too slow)
     */
    public void step(float dt) {
        level.world.step(Math.min(dt, 0.5f), 6, 2);
    }

    public void renderMap(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void renderWorld(OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        entityManager.draw(batch);
        batch.end();
    }

    /**
     * Invoke `render` on a Box2DDebugRenderer to draw the physics going on in this world.
     * Used for debugging.
     */
    public void renderPhysics(Box2DDebugRenderer renderer, OrthographicCamera camera) {
        renderer.render(level.world, camera.combined);
    }

    // TODO: Configure changes from SpawnController
    public void spawnEntity(WorldEntity worldEntity, SpawnController spawnController) {
        spawnController.spawn(worldEntity);
    }

    public float getUnitScale() {
        return level.getUnitScale();
    }

    public WorldEntityManager getEntityManager() {
        return entityManager;
    }

    public void dispose() {
        map.dispose();
    }
}
