package core.screens.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.ScreenManager;

public class HowToPlayScreen extends Menu {

    private final Screen prevScreen;
    private final String instructions;
    private final BitmapFont font;
    public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
    private final SpriteBatch batch;

    public HowToPlayScreen(ScreenManager screenManager, Screen prevScreen) {
        super(screenManager);
        this.prevScreen = prevScreen;
        instructions = """
                Welcome to WhiteKnights!\s
                You can pause and resume the game by pressing "ESC".\s
                You can move around with "WASD".\s
                You can open your inventory by pressing "I".\s
                Select an item using your mouse.\s
                Your selected item can be used by pressing "U".\s
                You will have defenders and weapons in your inventory. \s
                Enemies will spawn and attack your castle walls. \s
                Defeat all enemies to beat a level.\s
                \s
                Have fun! \s
                """;
        font = new BitmapFont(Gdx.files.internal("fonts/roboto.fnt"));
        font.setColor(Color.BLACK);

        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        super.show();
        stage.addActor(createBackButton());

    }

    @Override
    public void render(float delta){
        super.render(delta);
        batch.begin();

        font.draw(batch, instructions, Gdx.graphics.getWidth() >> 1, Gdx.graphics.getHeight() - 5, 1, 1, true);
        batch.end();
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    private ImageButton createBackButton() {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new Texture("buttons/back_button_inactive.png"));
        style.over = new TextureRegionDrawable(new Texture("buttons/back_button_active.png"));

        ImageButton button = new ImageButton(style);
        button.setPosition(5, 5);
        button.setSize(160, 80);

        button.addListener(createExitButtonListener(this.prevScreen));

        return button;
    }
}
