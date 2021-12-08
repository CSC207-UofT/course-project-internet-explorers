package core.input;

import java.util.HashSet;

public class InputManager {

    private final HashSet<InputMapping<?>> inputMappings = new HashSet<>();

    public void handleInputs() {
        inputMappings.forEach(InputMapping::mapInput);
    }

    public void addInputMapping(InputMapping<?> mapping) {
        inputMappings.add(mapping);
    }
}
