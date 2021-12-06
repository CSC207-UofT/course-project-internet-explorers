package core.input;

import core.levels.LevelManager;
import core.presenters.HUD.HudPresenter;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.Character;
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
    protected final WorldEntityManager worldEntityManager;
    protected final CharacterManager characterManager;
    protected final HudPresenter hudPresenter;
    protected final AIInputDevice aiInputDevice;
    protected final KeyboardInputDevice keyboardInputDevice;
    protected final LevelManager levelManager;

    public InputController(
        WorldEntityManager worldEntityManager,
        CharacterManager characterManager,
        HudPresenter hudPresenter,
        LevelManager levelManager
    ) {
        this.worldEntityManager = worldEntityManager;
        this.characterManager = characterManager;
        this.hudPresenter = hudPresenter;
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
        worldEntityManager
            .getEntities()
            .forEach((id, entity) -> {
                if (entity instanceof Character character) {
                    // Check what kind of input the character wants, then supply that input from our InputDevice
                    if (character.getInputDeviceType().equals(KeyboardInputDevice.class)) {
                        characterInputMap.put(id, keyboardInputDevice.getCharacterInput());
                    } else if (character.getInputDeviceType().equals(AIInputDevice.class)) {
                        characterInputMap.put(id, aiInputDevice.getCharacterInput());
                    } else {
                        // If no input type is set, use default inputs (which do nothing)
                        characterInputMap.put(id, CharacterInputDevice.DEFAULT.getCharacterInput());
                    }
                }
            });

        characterManager.processInputs(dt, characterInputMap);

        if (keyboardInputDevice.getHudInput().toggleInventory()) {
            hudPresenter.toggleInventory();
        }

        if (keyboardInputDevice.getHudInput().togglePause()) {
            hudPresenter.togglePauseWindow();

            if (!levelManager.isLevelPaused()) {
                levelManager.pause();
            } else {
                levelManager.resume();
            }
        }
    }
}
