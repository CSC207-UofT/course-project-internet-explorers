package core.worldEntities.types.characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import core.input.InputSchema;
import core.inventory.Item;
import core.worldEntities.WorldEntityWithSprite;
import core.worldEntities.collisions.CollisionBehaviour;
import core.worldEntities.collisions.HasCollisionBehaviours;
import core.worldEntities.health.DealsDamage;
import core.worldEntities.health.TakesDamage;
import java.util.ArrayList;

public class Character extends WorldEntityWithSprite implements TakesDamage, HasCollisionBehaviours {

    public static class Input extends InputSchema {

        private final Vector2 direction;
        private final boolean using;

        public Input(Vector2 direction, boolean using) {
            this.direction = direction;
            this.using = using;
        }

        public Input(Input input) {
            this(input.direction, input.using);
        }

        public Vector2 direction() {
            return direction.cpy();
        }

        public boolean using() {
            return using;
        }
    }

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
    private final ArrayList<CollisionBehaviour<?, ?>> collisionBehaviours = new ArrayList<>();

    public Character(Body body) {
        super(body);
        this.addCollisionBehaviour(TakesDamage.takeDamageOnCollision);
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

    @Override
    public ArrayList<CollisionBehaviour<?, ?>> getCollisionBehaviours() {
        return this.collisionBehaviours;
    }

    public void addCollisionBehaviour(CollisionBehaviour<?, ?> collisionBehaviour) {
        this.collisionBehaviours.add(collisionBehaviour);
    }
}
