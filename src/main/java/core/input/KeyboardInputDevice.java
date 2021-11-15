package core.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class KeyboardInputDevice implements InputDevice {

    @Override
    public CharacterInput getInput() {
        return new CharacterInput(getDirection(), getUsing(), getInventory());
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
        return Gdx.input.isKeyPressed(Input.Keys.U);
    }

    private boolean getInventory() {
        return Gdx.input.isKeyPressed(Input.Keys.I);
    }
}
