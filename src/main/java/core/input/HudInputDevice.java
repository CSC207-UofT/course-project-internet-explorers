package core.input;

public interface HudInputDevice {
    HudInput getHudInput();

    HudInputDevice DEFAULT = () -> new HudInput(false);
}
