package game_characters_test;

import static org.junit.Assert.assertEquals;

import game_characters.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EnemyTest {

    Enemy test_enemy;

    @BeforeEach
    void setup() {
        test_enemy = new Enemy(100, 1);
    }

    @Test
    void testHealthAttribute() {
        assertEquals(100, test_enemy.health);
    }

    @Test
    void testLevelAttribute() {
        assertEquals(1, test_enemy.level);
    }
}
