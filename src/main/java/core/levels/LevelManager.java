package core.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import core.world.WorldManager;

/**
 * Use-Case class for LevelState.
 * Implements methods for rendering level and advancing the level's World.
 */
public class LevelManager {

    private final LevelState level;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final WorldManager worldManager;

    public LevelManager(LevelState level) {
        this.level = level;
        map = new TmxMapLoader().load(level.getMapPath());
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, level.getUnitScale());
        this.worldManager = new WorldManager(level.world);
    }

    public void renderMap(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void renderWorld(OrthographicCamera camera) {
        worldManager.draw(camera);
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
}
