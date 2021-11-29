package core.worldEntities.types.characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import core.input.CharacterInputDevice;
import core.inventory.Item;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.WorldEntityWithSprite;
import core.worldEntities.collisions.CollisionBehaviour;
import core.worldEntities.collisions.HasCollisionBehaviour;
import core.worldEntities.health.DealsDamage;
import core.worldEntities.health.TakesDamage;
import java.util.ArrayList;

public class Character extends WorldEntityWithSprite implements TakesDamage, HasCollisionBehaviour<Character> {

    /*
     * Class that defines the main attributes of the classes Player, Enemy and Defender in the game.
     * @param health: The current health the GameCharacter has
     * @param level: The level of the GameCharacter
     * @param team: either player, defense or enemy to denote enemies and allies
     * @param inventory: The Items the GameCharacter is able to use at a given point in the game
     * */

    private String team;
    private float health;
    private int level;
    private ArrayList<Item> inventory;
    private Class<? extends CharacterInputDevice> inputDeviceType;
    private ArrayList<CollisionBehaviour<Character, ?>> collisionBehaviours;

    public Character(WorldEntityManager entityManager, BodyDef bodyDef, FixtureDef... fixtureDefs) {
        super(entityManager, bodyDef, fixtureDefs);
        this.team = "";
        this.health = 0;
        this.level = 0;
        this.inventory = new ArrayList<>();

        this.collisionBehaviours = new ArrayList<>();
        this.collisionBehaviours.add(
                new CollisionBehaviour<>(
                    Character.class,
                    DealsDamage.class,
                    (character, dealsDamage) -> {
                        character.takeDamage(dealsDamage.dealDamage());
                        // TODO remove once we have a proper way of displaying health
                        System.out.println(health);
                    }
                )
            );
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

    // TODO remove
    public void setVelocity(Vector2 velocity) {
        this.getBody().setLinearVelocity(velocity);
    }

    public void setInputDeviceType(Class<? extends CharacterInputDevice> inputDeviceType) {
        this.inputDeviceType = inputDeviceType;
    }

    public Class<? extends CharacterInputDevice> getInputDeviceType() {
        return inputDeviceType;
    }

    @Override
    public ArrayList<CollisionBehaviour<Character, ?>> getCollisionBehaviour() {
        return this.collisionBehaviours;
    }
}
