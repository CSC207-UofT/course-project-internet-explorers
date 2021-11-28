package core.input;

import com.badlogic.gdx.math.Vector2;

public class AIInputDevice implements CharacterInputDevice {

    @Override
    public CharacterInput getCharacterInput() {
        return new CharacterInput(new Vector2(-1, 0), false);
    }
}
