package core.levels;

import java.util.UUID;
import java.util.function.Consumer;

public class LevelEvent implements Comparable<LevelEvent> {

    private final float time;
    private final Consumer<LevelManager> eventCallback;
    private final UUID id;

    public LevelEvent(float time, Consumer<LevelManager> eventCallback) {
        this.time = time;
        this.eventCallback = eventCallback;
        this.id = UUID.randomUUID();
    }

    protected Consumer<LevelManager> getEventCallback() {
        return eventCallback;
    }

    @Override
    public int compareTo(LevelEvent o) {
        int timeDiff = Float.compare(this.time, o.time);

        return timeDiff == 0 ? this.id.compareTo(o.id) : timeDiff;
    }
}
