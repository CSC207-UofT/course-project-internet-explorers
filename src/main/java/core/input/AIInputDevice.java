package core.input;

import com.badlogic.gdx.math.Vector2;

public class AIInputDevice implements InputDevice {

    @Override
    public CharacterInput getInput() {
        return new CharacterInput(new Vector2(-1, 0), false, false);
    }
}
