import static org.junit.jupiter.api.Assertions.*;
import core.InventorySystem.Item;
import core.InventorySystem.ItemTypes.*;
import core.characters.GameCharacter;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestGameCharacter {

    GameCharacter test_player;

    @BeforeEach
//    void setup() {
//        ArrayList<Item> items = new ArrayList<>();
//        items.add((Item) new Sword(2));
//        items.add((Item) new Dagger(1));
//        test_player = new GameCharacter("square", "player", 100, 1, items);
//    } TODO Need to update shape body

    @Test
    void testHealthAttribute() {
        assertEquals(100, test_player.getHealth());
    }

    @Test
    void testLevelAttribute() {
        assertEquals(1, test_player.getLevel());
    }

    @Test
    void testInventoryAttribute() {
        ArrayList<Item> test_items = new ArrayList<>();
        test_items.add((Item) new Sword(2));
        test_items.add((Item) new Dagger(1));
        assertEquals(test_items, test_player.inventory);
    }
}
