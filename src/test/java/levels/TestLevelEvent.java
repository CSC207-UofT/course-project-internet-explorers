package levels;

import core.levels.LevelEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestLevelEvent {

    @Test
    void testCompareTimes() {
        Float floatA = (float) Math.random();
        Float floatB = (float) Math.random();

        LevelEvent eventA = new LevelEvent(floatA, m -> {});
        LevelEvent eventB = new LevelEvent(floatB, m -> {});

        if (!floatA.equals(floatB)) {
            Assertions.assertEquals(floatA.compareTo(floatB), eventA.compareTo(eventB));
            Assertions.assertEquals(floatB.compareTo(floatA), eventB.compareTo(eventA));
        }
    }

    @Test
    void testIdenticalTimes() {
        float time = (float) Math.random();

        LevelEvent eventA = new LevelEvent(time, m -> {});
        LevelEvent eventB = new LevelEvent(time, m -> {});

        // LevelEvents should be unique
        Assertions.assertNotEquals(eventA, eventB);
    }
}
