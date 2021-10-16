package game_characters_test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game_characters.Enemy;

public class EnemyTest {

    Enemy test_enemy;

    @BeforeEach
    void setup(){
        test_enemy = new Enemy(100, 1);
    }

    @Test
    void testHealthAttribute(){
        assertEquals(100, test_enemy.health);
    }

    @Test
    void testLevelAttribute(){
        assertEquals(1, test_enemy.level);
    }
}
