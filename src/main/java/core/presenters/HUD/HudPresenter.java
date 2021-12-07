package core.presenters.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import core.levels.LevelManager;
import core.worldEntities.types.characters.CharacterManager;

import java.io.IOException;
import java.util.UUID;

/***
 * Presenter class for displaying all heads-up-display
 */
public class HudPresenter implements Disposable {

    private final Stage stage;

    //Labels for displaying game info on the overlay
    private final Label countTimeLabel;
    private final Label winLabel;

    private boolean inventoryIsOpen;
    private final InventoryWindow playerInventory;

    private boolean isPauseOpen;
    private final PauseWindow pauseWindow;

    private final SpriteBatch sb;

    private final LevelManager levelManager;

    public HudPresenter(CharacterManager characterManager, LevelManager levelManager, UUID id) {
        //define tracking variables
        this.levelManager = levelManager;
        sb = new SpriteBatch();

        playerInventory = new InventoryWindow(characterManager, id);
        pauseWindow = new PauseWindow(this);

        Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);

        BitmapFont winFont = new BitmapFont();
        winFont.getData().setScale(2);

        //define labels using the String, and a Label style consisting of a font and color
        countTimeLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label scoreLabel = new Label("0", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label linkLabel = new Label("POINTS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        winLabel = new Label("", new Label.LabelStyle(winFont, Color.WHITE));
        winLabel.setAlignment(Align.center);

        //define a table used to organize hud's labels
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //add labels to table, padding the top, and giving them all equal width with expandX
        table.add(linkLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(countTimeLabel).expandX();
        table.row();
        table.add(winLabel).colspan(2).height(400);

        //add table to the stage
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    /***
     * Draws the overlay
     */
    public void draw() {
        sb.setProjectionMatrix(getCamera().combined);

        // display winning text once you beat the level
        if (levelManager.checkWin()) {
            winLabel.setText("YOU BEAT THIS LEVEL");
        }

        // updates the time label continuously
        countTimeLabel.setText(this.levelManager.getTime());
        stage.draw();
    }

    /***
     * @return the camera used for the HUD
     */
    public Camera getCamera() {
        return stage.getCamera();
    }

    /***
     * Disposes all HUD related elements (e.g. used when you move to a different screen)
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

    /***
     * Toggles the inventory window by adding or removing it to/from the stage
     */
    public void toggleInventory() {
        if (inventoryIsOpen) {
            playerInventory.remove();
        } else {
            stage.addActor(playerInventory);
        }
        inventoryIsOpen = !inventoryIsOpen;
    }

    /***
     * Toggles the inventory window by adding or removing it to/from the stage
     */
    public void togglePauseWindow() {
        if (isPauseOpen) {
            pauseWindow.remove();
        } else {
            stage.addActor(pauseWindow);
        }
        isPauseOpen = !isPauseOpen;
    }

    public void saveState() throws IOException {
        levelManager.saveState("LevelOne");
    }
}
