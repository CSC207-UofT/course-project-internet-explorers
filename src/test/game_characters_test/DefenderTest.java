package game_characters_test;

import static org.junit.Assert.assertEquals;

import game_characters.Defender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefenderTest {

    Defender test_defender;

    @BeforeEach
    void setup() {
        test_defender = new Defender(100, 1);
    }

    @Test
    void testHealthAttribute() {
        assertEquals(100, test_defender.health);
    }

    @Test
    void testLevelAttribute() {
        assertEquals(1, test_defender.level);
    }
}
