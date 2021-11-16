package core.screens.HUD;

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
import core.characters.CharacterManager;
import core.levels.LevelManager;

import java.util.UUID;

/***
 * Controller/Presenter class (?) for displaying all heads-up-display
 */
public class HudManager implements Disposable {

    private Stage stage;
    private Viewport viewport;

    //score && time tracking variables
    private Integer worldTimer;
    private static Integer score;
    private boolean timeUp;

    //Labels for displaying game info on the overlay
    private Label countTimeLabel, timeLabel, linkLabel, winLabel;
    private static Label scoreLabel;

    private boolean inventoryIsOpen;
    private InventoryWindow playerInventory;

    private SpriteBatch sb;

    private LevelManager levelManager;

    public HudManager(CharacterManager characterManager, LevelManager levelManager, UUID id) {
        //define tracking variables
        this.levelManager = levelManager;
        sb = new SpriteBatch();

        worldTimer = 0;
        score = 0;

        playerInventory = new InventoryWindow(characterManager, id);

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);

        BitmapFont winFont = new BitmapFont();
        winFont.getData().setScale(2);

        //define labels using the String, and a Label style consisting of a font and color
        countTimeLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("LEFTOVER TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        linkLabel = new Label("POINTS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
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
        if (levelManager.checkWin()){
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

    // These methods are unused at the moment, but may be useful later
    public boolean isTimeUp() {
        return timeUp;
    }

    public static Label getScoreLabel() {
        return scoreLabel;
    }

    public static Integer getScore() {
        return score;
    }
}
