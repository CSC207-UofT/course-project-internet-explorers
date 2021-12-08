package core.input;

import core.input.devices.AIInputDevice;
import core.input.devices.KeyboardInputDevice;

/**
 * Controller class for InputDevices.
 * Currently, it does not control all that much since AI Input is very simple.
 */
public class InputController {

    private static final AIInputDevice aiInputDevice = new AIInputDevice();
    private static final KeyboardInputDevice keyboardInputDevice = new KeyboardInputDevice();

    public static KeyboardInputDevice keyboardInputDevice() {
        return keyboardInputDevice;
    }

    public static AIInputDevice aiInputDevice() {
        return aiInputDevice;
    }
}
