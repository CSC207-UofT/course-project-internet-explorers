package characterTests;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import core.inventory.Item;
import core.inventory.items.*;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.GameCharacter;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCharacter {

    GameCharacter player;

    @BeforeEach
    public void setup() {
        player = new GameCharacter();
    }

    @Test
    public void testSetters() {
        player.setTeam("defender");
        player.setHealth(100);
        player.setLevel(1);
        ArrayList<Item> testItems = new ArrayList<>();
        testItems.add(new Sword(2));
        testItems.add(new Dagger(1));
        player.setInventory(testItems);

        assertEquals("defender", player.getTeam());
        assertEquals(100, player.getHealth());
        assertEquals(1, player.getLevel());
        assertEquals(testItems, player.getInventory());
    }
}
