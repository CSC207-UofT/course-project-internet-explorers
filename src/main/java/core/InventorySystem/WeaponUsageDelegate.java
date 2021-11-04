package core.InventorySystem;

import core.characters.CharacterManager;
import core.characters.GameCharacter;
//TODO make ID based instead of name based / collaborate with WorldEntity
import java.util.UUID;

public class WeaponUsageDelegate implements ItemUsageDelegate {

    private GameCharacter character;

    public WeaponUsageDelegate(GameCharacter character) {
        this.character = character;
    }
    //Ensures character is holding the most recent selected item iff item is in character's inventory

    public boolean hold(Weapon weapon) {
        if (this.character.inventory.contains(weapon)){
            weapon.setHeld();
        }
        else {
            return false;
        }
        return true;
    }

    //Use the weapon on a GameCharacter
    public void use(Weapon weapon, GameCharacter character) {
        if (weapon.getHeld()){
            //If the weapon is held, the distance between the two characters is measured to determine if in range
            //TODO character.getPosition implementation
            double distance = Math.hypot(character.getPosition[0]-this.character.getPosition[0],
                    character.getPosition[1]-this.character.getPosition[1]);
            if (Math.abs(distance) <= weapon.getRange()){
                //TODO implement and call collide() and getter for character.ID
                //Should all manager methods be static? TODO fix depleteHealth call
                CharacterManager.depleteHealth(character.getID(),weapon.getDamage());
                weapon.setLevel(weapon.getLevel()-1);
        }
    }

}}
