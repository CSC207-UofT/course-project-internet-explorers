package core.characters;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import core.world.WorldEntity;
import core.InventorySystem.*;

import java.util.ArrayList;
import java.util.UUID;
import core.world.WorldEntity;

import java.util.ArrayList;

public class GameCharacter extends WorldEntity {

    /*
     * Class that defines the main attributes of the classes Player, Enemy and Defender in the game.
     * @param health: The current health the GameCharacter has
     * @param level: The level of the GameCharacter
     * @param team: either player, defense or enemy to denote enemies and allies
     * @param inventory: The Items the GameCharacter is able to use at a given point in the game
     * */

    private final String team;
    private int health;
    private int level;
    private ArrayList<Item> inventory;

    public GameCharacter(Body body) {
        super(body);
        this.team = "";
        this.health = 0;
        this.level = 0;
        this.inventory = new ArrayList<Item>();
    }

    public UUID getId() {
        return this.id;
    }

    public String getTeam() { return this.team; }

    public int getHealth() { return this.health; }

    public void setHealth(int health) { this.health = health;}

    public int getLevel() { return this.level; }

    public void setLevel(int level) { this.level = level; }

    public ArrayList<Item> getInventory() { return this.inventory; }

    public void setInventory(ArrayList<Item> inventory) { this.inventory = inventory;}


    public void move(Vector2 velocity){
        this.getBody().setLinearVelocity(velocity);
    }

    //I feel like we may need a player class for this?
    public void openInventory(){}
    public void useItem(){}
}

