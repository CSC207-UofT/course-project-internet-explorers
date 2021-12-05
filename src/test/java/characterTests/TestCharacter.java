package characterTests;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import core.inventory.Item;
import core.inventory.items.*;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.Character;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCharacter {

    Character player;
    World world;
    WorldEntityManager entityManager;

    @BeforeEach
    public void setup() {
        world = new World(new Vector2(0, 0), true);
        entityManager = new WorldEntityManager(world);

        player = entityManager.createEntity(Character.class, new BodyDef());
    }

    @AfterEach
    public void teardown() {
        world.dispose();
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
