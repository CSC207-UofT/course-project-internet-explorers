package core.input;

import core.characters.GameCharacter;
import java.util.HashMap;
import java.util.UUID;

/**
 * Use-case class which manages InputDevices for GameCharacters
 */
public class InputManager {

    private final HashMap<UUID, InputDevice> characterInputDevices;

    public InputManager() {
        this.characterInputDevices = new HashMap<>();
    }

    public HashMap<UUID, CharacterInput> getInputs() {
        HashMap<UUID, CharacterInput> res = new HashMap<>();
        characterInputDevices.forEach((id, device) -> res.put(id, device.getInput()));
        return res;
    }

    public void addInputDeviceFor(GameCharacter character, InputDevice inputDevice) {
        characterInputDevices.put(character.id, inputDevice);
    }
}
