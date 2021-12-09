package core.input.devices;

import com.badlogic.gdx.math.Vector2;
import core.worldEntities.types.characters.Character;
import java.util.function.Supplier;

public class AIInputDevice {

    private final Supplier<Character.Input> characterInputSupplier = () -> new Character.Input(new Vector2(-1, 0), false);

    public Supplier<Character.Input> characterInputProvider() {
        return characterInputSupplier;
    }
}
