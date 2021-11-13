package characterTests;

import core.characters.CharacterManager;
import core.characters.GameCharacter;
import com.badlogic.gdx.physics.box2d.*;
import org.junit.Before;
import org.testng.annotations.Test;
import java.util.List;

import static org.junit.Assert.*;

public class TestCharacterManager {

    /*
    * Tests the CharacterManager use case class
    * */

    CharacterManager cm;

    @Before
    public void setUp() {
        cm = new CharacterManager();
    }

    @org.junit.Test
    public void testAddCharacter() {

    }
}
