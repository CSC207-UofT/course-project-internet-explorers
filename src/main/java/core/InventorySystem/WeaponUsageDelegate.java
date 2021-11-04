package core.InventorySystem;

import core.characters.GameCharacter;

public class WeaponUsageDelegate implements ItemUsageDelegate {

    private GameCharacter character;

    public WeaponUsageDelegate(GameCharacter character) {
        this.character = character;
    }
    //Ensures character is holding the most recent selected item iff item is in character's inventory
    @Override
    public boolean hold(Item item) {
        if (this.character.inventory.contains(item)){
            //TODO add setter method
            item.held = true;
        }
        else {
            return false;
        }
        return true;
    }

    @Override
    public void use(Item item) {
        if (item.held()){
        //TODO
        }
    }
}
