import static org.junit.jupiter.api.Assertions.*;

import core.characters.GameCharacter;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestGameCharacter {

    GameCharacter test_player;

    @BeforeEach
    void setup() {
        ArrayList<String> items = new ArrayList<>();
        items.add("Sword");
        items.add("Defender: Archer");
        test_player = new GameCharacter("defenders", 100, 1, items);
    }

    @Test
    void testHealthAttribute() {
        assertEquals(100, test_player.health);
    }

    @Test
    void testLevelAttribute() {
        assertEquals(1, test_player.level);
    }

    @Test
    void testInventoryAttribute() {
        ArrayList<String> test_items = new ArrayList<>();
        test_items.add("Sword");
        test_items.add("Defender: Archer");
        assertEquals(test_items, test_player.inventory);
    }
}
