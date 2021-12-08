package headless;

import static org.mockito.Mockito.mock;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import core.config.Config;
import core.presenters.levels.LevelGameplayController;
import desktop.DesktopConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests which need to run with graphics disabled.
 */
public class HeadlessTests {

    static final String graphicsSettingName = "render-graphics";

    private static void testRenderGraphicsSetting() {
        Assertions.assertNotNull(DesktopConfig.renderGraphics, "renderGraphics setting does not exist");

        Assertions.assertDoesNotThrow(() -> Config.get(graphicsSettingName), "Graphics rendering setting not found");

        Assertions.assertEquals(true, Config.get(graphicsSettingName), "Couldn't get rendering setting value");

        Assertions.assertDoesNotThrow(() -> Config.set(graphicsSettingName, "false"), "couldn't set rendering setting to 'false'");

        Assertions.assertEquals(false, Config.get(graphicsSettingName), "Rendering setting not actually set to false");
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
}
