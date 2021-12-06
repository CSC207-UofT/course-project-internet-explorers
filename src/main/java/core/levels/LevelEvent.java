package core.levels;

import java.util.function.Consumer;

public class LevelEvent implements Comparable<LevelEvent> {

    private final float time;
    private final Consumer<LevelManager> eventCallback;

    public LevelEvent(float time, Consumer<LevelManager> eventCallback) {
        this.time = time;
        this.eventCallback = eventCallback;
    }

    public float getTime() {
        return time;
    }

    protected Consumer<LevelManager> getEventCallback() {
        return eventCallback;
    }

    @Override
    public int compareTo(LevelEvent o) {
        return (int) (this.time - o.getTime());
    }
}
