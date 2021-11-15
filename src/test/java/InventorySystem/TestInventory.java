package InventorySystem;

import static org.junit.jupiter.api.Assertions.*;

import core.InventorySystem.*;
import core.InventorySystem.Item;
import core.InventorySystem.ItemTypes.*;
import core.characters.CharacterManager;
import core.characters.GameCharacter;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class TestInventory {

    GameCharacter test_player;

    @Test
    void testDamage() {
        Item sword = (Item) new Sword(1);
        Item dagger = (Item) new Dagger(2);
        test_player.getInventory().add(sword);
        test_player.getInventory().add(dagger);
        Weapon weapon = (Weapon) test_player.getInventory().get(0);
        assertEquals(3, weapon.getDamage());
    }

    @Test
    void testInventory() {
        Item sword = (Item) new Sword(1);
        Item dagger = (Item) new Dagger(2);
        test_player.getInventory().add(sword);
        test_player.getInventory().add(dagger);
        CharacterManager manager = new CharacterManager();
        manager.addCharacter(test_player);
        ArrayList<Item> inventory = (ArrayList<Item>) manager.openInventory(test_player.id);
        ArrayList<Item> comparison = new ArrayList<>();
        comparison.add(sword);
        comparison.add(dagger);
        assertEquals(comparison, inventory);
    }

    @Test
    void testSelect() {
        Item sword = (Item) new Sword(1);
        Item dagger = (Item) new Dagger(2);
        test_player.getInventory().add(sword);
        test_player.getInventory().add(dagger);
        CharacterManager manager = new CharacterManager();
        manager.addCharacter(test_player);
        assertEquals(manager.selectItem(test_player.id, test_player.getInventory().get(test_player.getInventory().indexOf(sword))), true);
    }
}
