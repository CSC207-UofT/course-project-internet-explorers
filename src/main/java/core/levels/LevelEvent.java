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

    public UUID getId() {
        return this.id;
    }

    public float getTime() {
        return time;
    }

    protected Consumer<LevelManager> getEventCallback() {
        return eventCallback;
    }

    @Override
    public int compareTo(LevelEvent o) {
        int timeDiff = (int) (this.time * 100000 - o.getTime() * 100000);
        if (timeDiff == 0) {
            return this.id.compareTo(o.getId());
        }
        return timeDiff;
    }
}
