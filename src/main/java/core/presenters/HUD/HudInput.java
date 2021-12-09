package core.presenters.HUD;

import core.input.InputSchema;

public class HudInput extends InputSchema {

    private final boolean toggleInventory;
    private final boolean togglePause;

    public HudInput(boolean toggleInventory, boolean togglePause) {
        this.toggleInventory = toggleInventory;
        this.togglePause = togglePause;
    }

    public boolean toggleInventory() {
        return toggleInventory;
    }

    public boolean togglePause() {
        return togglePause;
    }
}
