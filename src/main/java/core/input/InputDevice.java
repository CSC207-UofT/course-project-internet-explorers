package core.input;

import com.badlogic.gdx.math.Vector2;

public interface InputDevice {
    CharacterInput getInput();

    InputDevice DEFAULT = () -> new CharacterInput(new Vector2(), false, false);
}
