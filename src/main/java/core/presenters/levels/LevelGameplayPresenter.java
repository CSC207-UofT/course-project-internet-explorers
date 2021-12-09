package core.presenters.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import core.config.Config;
import core.config.ConfigurableSetting;
import core.levels.LevelManager;

/**
 * Runs the gameplay of a Level.
 */
public class LevelGameplayPresenter {

    private final ShapeRenderer shapeRenderer;
    private final LevelGameplayController levelGameplayController;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final SpriteBatch batch;

    private final ConfigurableSetting<Boolean> render_physics = Config.add(Boolean.class,
                                                                           "render_physics",
                                                                           "Whether physics bodies should be rendered.",
                                                                           false,
                                                                           Boolean::parseBoolean
    );

    public LevelGameplayPresenter(LevelGameplayController levelGameplayController) {
        this(levelGameplayController, new Box2DDebugRenderer(), new ShapeRenderer(), new OrthogonalTiledMapRenderer(
                levelGameplayController.getLevelManager().getMap(),
                levelGameplayController.getLevelManager().getLevelUnitScale()
        ), new SpriteBatch());
    }

    public LevelGameplayPresenter(LevelGameplayController levelGameplayController, Box2DDebugRenderer box2DDebugRenderer, ShapeRenderer shapeRenderer, OrthogonalTiledMapRenderer mapRenderer, SpriteBatch spriteBatch) {
        this.levelGameplayController = levelGameplayController;
        this.box2DDebugRenderer = box2DDebugRenderer;
        this.shapeRenderer = shapeRenderer;
        this.mapRenderer = mapRenderer;
        this.batch = spriteBatch;
    }


    public void renderMap() {
        mapRenderer.setView(levelGameplayController.getCameraManager().getCamera());
        mapRenderer.render();
    }

    public void renderWorld() {
        batch.setProjectionMatrix(levelGameplayController.getCameraManager().getCamera().combined);
        batch.begin();
        levelGameplayController.getEntityManager().draw(batch);
        batch.end();
    }

    /**
     * Invoke `render` on a Box2DDebugRenderer to draw the physics going on in this world.
     * Used for debugging.
     */
    public void renderPhysics() {
        if (render_physics.get()) {
            box2DDebugRenderer.render(levelGameplayController.getLevelManager().getWorld(),
                                      levelGameplayController.getCameraManager().getCamera().combined
            );
        }
    }

    public void render() {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderMap();
        renderWorld();

        // TODO move following code to the CameraManager
        // draw a red dot which marks the spot tracked by the camera (for debugging)
        shapeRenderer.setProjectionMatrix(levelGameplayController.getCameraManager().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(levelGameplayController.getCameraManager().getSubjectPosition().x,
                             levelGameplayController.getCameraManager().getSubjectPosition().y,
                             0.1f,
                             16
        );
        shapeRenderer.end();

        // TODO only do this in debug mode
        renderPhysics();

        if ((boolean) Config.get("render-graphics")) {
            levelGameplayController.getHudPresenter().draw();
        }
    }

    public void resize() {
        levelGameplayController.getCameraManager().syncCameraViewportToWindow();
    }
}
