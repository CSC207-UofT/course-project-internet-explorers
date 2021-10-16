package core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.GdxGame;

public class MainMenuScreen implements Screen {

    private final Game game;
    private final Stage stage;

    private static final int EXIT_BUTTON_WIDTH = 300;
    private static final int EXIT_BUTTON_HEIGHT = 150;
    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 150;
    private static final int EXIT_BUTTON_X = GdxGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
    private static final int EXIT_BUTTON_Y = 100;
    private static final int PLAY_BUTTON_X = GdxGame.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
    private static final int PLAY_BUTTON_Y = 300;

    private ImageButton createPlayButton() {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new Texture("buttons/play_button_inactive.png"));
        style.over = new TextureRegionDrawable(new Texture("buttons/play_button_active.png"));

        ImageButton button = new ImageButton(style);
        button.setPosition(PLAY_BUTTON_X, PLAY_BUTTON_Y);
        button.setSize(PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);

        button.addListener(
            new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    game.setScreen(new GameScreen());
                }
            }
        );

        return button;
    }

    private ImageButton createExitButton() {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new Texture("buttons/exit_button_inactive.png"));
        style.over = new TextureRegionDrawable(new Texture("buttons/exit_button_active.png"));

        ImageButton button = new ImageButton(style);
        button.setPosition(EXIT_BUTTON_X, EXIT_BUTTON_Y);
        button.setSize(EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);

        button.addListener(
            new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.exit();
                }
            }
        );

        return button;
    }

    public MainMenuScreen(GdxGame game) {
        this.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(createPlayButton());
        stage.addActor(createExitButton());
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
