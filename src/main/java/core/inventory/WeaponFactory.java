package core.inventory;

import core.inventory.Weapon;
import core.inventory.items.Dagger;
import core.inventory.items.Defender;
import core.inventory.items.Sword;

public class WeaponFactory {

    public static Weapon getWeapon(String type){
        if("Dagger".equalsIgnoreCase(type)) return new Dagger();
        else if("Sword".equalsIgnoreCase(type)) return new Sword();
        else if ("Defender".equalsIgnoreCase(type)) return new Defender();
        return null;
    }
}