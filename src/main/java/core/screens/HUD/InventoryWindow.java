package core.screens.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import core.screens.SpritesDemoScreen;
import core.screens.menus.MainMenuScreen;

public class InventoryWindow extends Window {

    public InventoryWindow() {
        super("Inventory", new Skin(Gdx.files.internal("skins/uiskin.json")));
        this.setSize(400, 300);
        this.setResizable(false);
        this.setMovable(false);
        this.setPosition(300, 300);
        this.add(createSpriteDemoButton());

    }
    private TextButton createSpriteDemoButton() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.overFontColor = Color.BLACK;
        TextButton button = new TextButton("Sprites Demo", style);

        button.setPosition(500, 600);

        button.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        System.out.println("testing inv button");
                    }
                }
        );
        return button;
    }
}
