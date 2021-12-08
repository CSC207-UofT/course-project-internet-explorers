package core.input;

import java.util.HashSet;

/**
 * Use-case class for storing & invoking input mappings.
 */
public class InputManager {

    private final HashSet<InputMapping<?>> inputMappings = new HashSet<>();

    public void handleInputs() {
        inputMappings.forEach(InputMapping::mapInput);
    }

    public void addInputMapping(InputMapping<?> mapping) {
        inputMappings.add(mapping);
    }
}
