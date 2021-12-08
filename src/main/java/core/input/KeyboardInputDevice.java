package core.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import core.worldEntities.types.characters.Character;

public class KeyboardInputDevice {

    private final InputProvider<Character.Input> characterInputProvider = () -> new Character.Input(getDirection(), getUsing());

    public InputProvider<Character.Input> characterInputProvider() {
        return characterInputProvider;
    }

    private final InputProvider<core.presenters.HUD.HudInput> hudInputInputProvider = () ->
        new core.presenters.HUD.HudInput(getInventory(), getPauseMenu());

    public InputProvider<core.presenters.HUD.HudInput> hudInputInputProvider() {
        return hudInputInputProvider;
    }

    private Vector2 getDirection() {
        int dx = 0;
        dx += Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : 0;
        dx -= Gdx.input.isKeyPressed(Input.Keys.A) ? 1 : 0;
        int dy = 0;
        dy += Gdx.input.isKeyPressed(Input.Keys.W) ? 1 : 0;
        dy -= Gdx.input.isKeyPressed(Input.Keys.S) ? 1 : 0;

        return new Vector2(dx, dy);
    }

    private boolean getUsing() {
        return Gdx.input.isKeyJustPressed(Input.Keys.U);
    }

    private boolean getInventory() {
        return Gdx.input.isKeyJustPressed(Input.Keys.I);
    }

    private boolean getPauseMenu() {
        return Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE);
    }
}
