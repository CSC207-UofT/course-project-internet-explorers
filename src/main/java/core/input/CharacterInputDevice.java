package core.input;

import com.badlogic.gdx.math.Vector2;

public interface CharacterInputDevice {
    CharacterInput getCharacterInput();

    CharacterInputDevice DEFAULT = () -> new CharacterInput(new Vector2(), false);
}
