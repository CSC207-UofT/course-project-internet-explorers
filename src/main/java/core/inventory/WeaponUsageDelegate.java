package core.inventory;

import java.util.UUID;

public class WeaponUsageDelegate {

    /*
     * Dagger weapon object stored in inventory for use
     * @param id: id of character who owns the UsageDelegate
     * */

    private final UUID id;

    public WeaponUsageDelegate(UUID id) {
        this.id = id;
    }

    //Use the weapon
    public void use(Weapon weapon) {
        //Placeholder use method
        System.out.println("Weapon is used");
    }
}
