package core.characters;

import java.util.ArrayList;
import java.util.UUID;

public class GameCharacter extends WorldEntity {

    /*
     * Class that defines the main attributes of the classes Player, Enemy and Defender in the game.
     * @param health: The current health the GameCharacter has
     * @param level: The level of the GameCharacter
     * @param team: either player, defense or enemy to denote enemies and allies
     * @param inventory: The Items the GameCharacter is able to use at a given point in the game
     * TODO: Change type of ArrayList to Item
     * TODO: Remove shape & merge when WorldEntity class is updated
     * */

    private String team;
    private int health;
    private int level;
    private ArrayList<String> inventory;
    private final UUID id;

    public GameCharacter(String shape, String team, int health, int level,
                         ArrayList<String> inventory) {
        super(shape);
        this.team = team;
        this.health = health;
        this.level = level;
        this.inventory = inventory;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return this.id;
    }

    public String getTeam() { return this.team; }

    public int getHealth() { return this.health; }

    public void setHealth(int health) { this.health = health;}

    public int getLevel() { return this.level; }

    public void setLevel(int level) { this.level = level; }

    public ArrayList<String> getInventory() { return this.inventory; }

    public void setInventory(ArrayList<String> inventory) { this.inventory = inventory;}
}


