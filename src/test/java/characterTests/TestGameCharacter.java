package characterTests;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import core.InventorySystem.Item;
import core.InventorySystem.ItemTypes.*;
import core.characters.GameCharacter;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestGameCharacter {

    GameCharacter player;

    @BeforeEach
    public void setup() {
        World world = new World(new Vector2(0, 0), true);
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;

        Body body = world.createBody(def);
        player = new GameCharacter(body);
    }

    @Test
    public void testSetters() {

        player.setTeam("defender");
        player.setHealth(100);
        player.setLevel(1);
        ArrayList<Item> testItems = new ArrayList<>();
        testItems.add((Item) new Sword(2));
        testItems.add((Item) new Dagger(1));
        player.setInventory(testItems);

        assertEquals("defender", player.getTeam());
        assertEquals(100, player.getHealth());
        assertEquals(1, player.getLevel());
        assertEquals(testItems, player.getInventory());
    }
}
