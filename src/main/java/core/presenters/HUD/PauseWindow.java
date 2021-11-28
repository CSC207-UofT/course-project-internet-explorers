package core.presenters.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseWindow extends Window {

    public PauseWindow() {
        super("Pause", new Skin(Gdx.files.internal("skins/uiskin.json")));
        this.setSize(400, 300);
        this.setResizable(false);
        this.setMovable(false);
        this.setPosition(300, 300);
        this.add(exitGameButton());
    }

    private TextButton exitGameButton() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.overFontColor = Color.WHITE;
        TextButton button = new TextButton("Exit Game", style);

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
}
