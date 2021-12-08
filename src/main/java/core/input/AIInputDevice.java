package core.input;

import com.badlogic.gdx.math.Vector2;
import core.worldEntities.types.characters.Character;

public class AIInputDevice {

    private final InputProvider<Character.Input> characterInputProvider = () -> new Character.Input(new Vector2(-1, 0), false);

    public InputProvider<Character.Input> characterInputProvider() {
        return characterInputProvider;
    }
}
