package headless;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import core.config.Config;
import core.presenters.ScreenController;
import core.presenters.levels.LevelGameplayController;
import core.presenters.levels.LevelGameplayPresenter;
import core.presenters.menus.MainMenuScreen;
import desktop.DesktopConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests which need to run with graphics disabled.
 */
public class HeadlessTests {

    static final String graphicsSettingName = "render-graphics";

    private static void testRenderGraphicsSetting() {
        assertNotNull(DesktopConfig.renderGraphics, "renderGraphics setting does not exist");

        assertDoesNotThrow(() -> Config.get(graphicsSettingName), "Graphics rendering setting not found");

        assertEquals(true, Config.get(graphicsSettingName), "Couldn't get rendering setting value");

        assertDoesNotThrow(() -> Config.set(graphicsSettingName, "false"), "couldn't set rendering setting to 'false'");

        assertEquals(false, Config.get(graphicsSettingName), "Rendering setting not actually set to false");
    }

    @BeforeAll
    static void setup() {
        Gdx.gl20 = mock(com.badlogic.gdx.graphics.GL20.class);
        Gdx.gl = Gdx.gl20;

        testRenderGraphicsSetting();

        Gdx.app =
            new HeadlessApplication(
                new Game() {
                    @Override
                    public void create() {}
                }
            );
    }

    @AfterAll
    static void teardown() {
        Gdx.app.exit();
    }

    @Test
    void testLevelGameplayController() {
        LevelGameplayController levelGameplayController = new LevelGameplayController();

        levelGameplayController.show();
        levelGameplayController.render(0.1f);
        levelGameplayController.hide();
        levelGameplayController.dispose();
    }

    @Test
    void testLevelTimeIncrements() {
        LevelGameplayController levelGameplayController = new LevelGameplayController();

        float totalTime = 10;
        float timeCounter = 0;

        levelGameplayController.show();

        while (timeCounter < totalTime) {
            float dt = (float) Math.random() / 2;

            levelGameplayController.render(dt);
            timeCounter += dt;
        }
        assertEquals(timeCounter, levelGameplayController.getLevelManager().getTime());

        levelGameplayController.hide();
        levelGameplayController.dispose();
    }

    @Test
    void testLevelGameplayControllerGetters() {
        LevelGameplayController levelGameplayController = new LevelGameplayController();
        assertDoesNotThrow(levelGameplayController::pause);
        assertDoesNotThrow(levelGameplayController::resume);
        assertDoesNotThrow(levelGameplayController::getHudPresenter);
        assertDoesNotThrow(levelGameplayController::getCharacterManager);
        assertDoesNotThrow(levelGameplayController::getLevelManager);
    }

//    @Test
//    void testMainMenuScreen() {
//        ScreenController screenController = new ScreenController();
//        Stage stage = mock(Stage.class);
//
//        MainMenuScreen mainMenuScreen = new MainMenuScreen(screenController, stage);
//
//    }

    @Test
    void testLevelGameplayPresenter() {
        LevelGameplayController levelGameplayController = new LevelGameplayController();
        levelGameplayController.show();

        LevelGameplayPresenter presenter = new LevelGameplayPresenter(levelGameplayController, mock(Box2DDebugRenderer.class), mock(
                ShapeRenderer.class), mock(OrthogonalTiledMapRenderer.class), mock(SpriteBatch.class));

        Config.set("render_physics", "true");

        assertDoesNotThrow(presenter::render);
        assertDoesNotThrow(presenter::resize);

        levelGameplayController.hide();
        levelGameplayController.dispose();
    }

}
