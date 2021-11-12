package core.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;


public class InputHandler {
    private final CharacterManager manager;
    public InputHandler (CharacterManager manager){
        this.manager = manager;
    }

    /***
     * This is the controller class that checks the keyboard input to calls the CharacterManager to move the
     * character correspondingly
     * @param id chacacter id
     */
    public void checkInputMovement(UUID id){
        int dx = 0;
        dx += Gdx.input.isKeyPressed(Input.Keys.W) ? 1 : 0;//the ? mark means true returns left and false return right
        dx -= Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : 0;
        int dy = 0;
        dy += Gdx.input.isKeyPressed(Input.Keys.W) ? 1 : 0;
        dy -= Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : 0;
        manager.updateCharacterPosition(id, dx, dy);
    }
}
