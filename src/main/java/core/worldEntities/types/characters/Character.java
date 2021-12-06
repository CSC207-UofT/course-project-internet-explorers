package core.worldEntities.types.characters;

import com.badlogic.gdx.physics.box2d.Body;
import core.input.CharacterInputDevice;
import core.inventory.Item;
import core.worldEntities.WorldEntityWithSprite;
import core.worldEntities.collisions.CollisionBehaviour;
import core.worldEntities.collisions.HasCollisionBehaviours;
import core.worldEntities.health.TakesDamage;
import java.util.ArrayList;

public class Character extends WorldEntityWithSprite implements TakesDamage, HasCollisionBehaviours {

    /*
     * Class that defines the main attributes of the classes Player, Enemy and Defender in the game.
     * @param health: The current health the GameCharacter has
     * @param level: The level of the GameCharacter
     * @param team: either player, defense or enemy to denote enemies and allies
     * @param inventory: The Items the GameCharacter is able to use at a given point in the game
     * */

    // measured in m/s
    public static float SPEED = 10f;

    private String team;
    private float health = 0;
    private int level = 0;
    private ArrayList<Item> inventory = new ArrayList<>();
    private Class<? extends CharacterInputDevice> inputDeviceType = CharacterInputDevice.class;
    private final ArrayList<CollisionBehaviour<?, ?>> collisionBehaviours = new ArrayList<>();

    public Character(Body body) {
        super(body);
        this.collisionBehaviours.add(TakesDamage.takeDamageOnCollision);
    }

    public String getTeam() {
        return this.team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public float getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(float health) {
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

    @Override
    public ArrayList<CollisionBehaviour<?, ?>> getCollisionBehaviours() {
        return this.collisionBehaviours;
    }
}
