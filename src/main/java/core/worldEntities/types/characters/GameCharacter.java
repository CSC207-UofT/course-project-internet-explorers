package core.worldEntities.types.characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import core.input.CharacterInputDevice;
import core.inventory.Item;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.WorldEntityWithSprite;
import java.util.ArrayList;

public class GameCharacter extends WorldEntityWithSprite {

    /*
     * Class that defines the main attributes of the classes Player, Enemy and Defender in the game.
     * @param health: The current health the GameCharacter has
     * @param level: The level of the GameCharacter
     * @param team: either player, defense or enemy to denote enemies and allies
     * @param inventory: The Items the GameCharacter is able to use at a given point in the game
     * */

    private String team;
    private int health;
    private int level;
    private ArrayList<Item> inventory;
    private Class<? extends CharacterInputDevice> inputDeviceType;

    public GameCharacter() {
        super();
        this.team = "";
        this.health = 0;
        this.level = 0;
        this.inventory = new ArrayList<>();
    }

    public String getTeam() {
        return this.team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public void setInputDeviceType(Class<? extends CharacterInputDevice> inputDeviceType) {
        this.inputDeviceType = inputDeviceType;
    }

    public Class<? extends CharacterInputDevice> getInputDeviceType() {
        return inputDeviceType;
    }
}
