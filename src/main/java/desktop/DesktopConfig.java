package desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import core.config.ConfigurableSetting;

// Configs will be accessed by reference in Phase 3 (coming soon)
@SuppressWarnings("unused")
public class DesktopConfig {

    protected static final LwjglApplicationConfiguration lwjglConfig = new LwjglApplicationConfiguration();

    public static final ConfigurableSetting<Boolean> renderGraphics = core.config.Config.add(
        Boolean.class,
        "render-graphics",
        "Whether graphics should be rendered.",
        true,
        Boolean::parseBoolean
    );

    public static final ConfigurableSetting<Integer> fps = core.config.Config.add(
        Integer.class,
        "fps",
        "FPS limit when game window is active.",
        120,
        () -> lwjglConfig.foregroundFPS,
        value -> lwjglConfig.foregroundFPS = value,
        Integer::parseUnsignedInt
    );

    public static final ConfigurableSetting<Boolean> resizable = core.config.Config.add(
        Boolean.class,
        "resizable",
        // TODO change note to (Requires restart) once saving/loading configs is implemented
        "Whether the game window is resizable (Cannot be changed on the fly).",
        false,
        () -> lwjglConfig.resizable,
        value -> lwjglConfig.resizable = value,
        Boolean::parseBoolean
    );

    public static final ConfigurableSetting<Integer> width = core.config.Config.add(
        Integer.class,
        "width",
        "Initial width of the game window.",
        1024,
        () -> lwjglConfig.width,
        value -> lwjglConfig.width = value,
        Integer::parseUnsignedInt
    );

    public static final ConfigurableSetting<Integer> height = core.config.Config.add(
        Integer.class,
        "height",
        "Initial height of the game window.",
        768,
        () -> lwjglConfig.height,
        value -> lwjglConfig.height = value,
        Integer::parseUnsignedInt
    );
}
