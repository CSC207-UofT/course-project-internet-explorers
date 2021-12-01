package core.input;

import core.levels.LevelManager;
import core.presenters.HUD.HudManager;
import core.worldEntities.types.characters.CharacterManager;
import java.util.HashMap;
import java.util.UUID;

/**
 * Use-case class which manages InputDevices for GameCharacters.
 * Routes different kinds of inputs to their respective use-cases.
 */
public class InputController {

    // input consumers: these use-case classes take inputs do something with them
    // TODO separate types of inputs
    protected final CharacterManager characterManager;
    protected final HudManager hudManager;
    protected final AIInputDevice aiInputDevice;
    protected final KeyboardInputDevice keyboardInputDevice;
    protected final LevelManager levelManager;

    public InputController(CharacterManager characterManager, HudManager hudManager, LevelManager levelManager) {
        this.characterManager = characterManager;
        this.hudManager = hudManager;
        this.levelManager = levelManager;
        this.aiInputDevice = new AIInputDevice();
        this.keyboardInputDevice = new KeyboardInputDevice();
    }

    /**
     * Handles various inputs, should be called every frame.
     * @param dt the frame time
     */
    public void handleInputs(float dt) {
        HashMap<UUID, CharacterInput> characterInputMap = new HashMap<>();
        characterManager.characterEntities.forEach((id, character) -> {
            // Check what kind of input the character wants, then supply that input from our InputDevice
            if (character.getInputDeviceType().equals(KeyboardInputDevice.class)) {
                characterInputMap.put(id, keyboardInputDevice.getCharacterInput());
            } else if (character.getInputDeviceType().equals(AIInputDevice.class)) {
                characterInputMap.put(id, aiInputDevice.getCharacterInput());
            } else {
                // If no input type is set, use default inputs (which do nothing)
                characterInputMap.put(id, CharacterInputDevice.DEFAULT.getCharacterInput());
            }
        });

        characterManager.processInputs(dt, characterInputMap);

        if (keyboardInputDevice.getHudInput().toggleInventory()) {
            hudManager.toggleInventory();
        }

        if (keyboardInputDevice.getHudInput().togglePause()) {
            hudManager.togglePauseWindow();

            if (!levelManager.isLevelPaused()) {
                levelManager.pause();
            } else {
                levelManager.resume();
            }
        }
    }
}