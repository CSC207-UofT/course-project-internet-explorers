package core.InventorySystem;

import java.util.UUID;
import java.util.ArrayList;

public class Inventory {
    /*
     * Each character has its own inventory object, instantiated as an ArrayList of items
     * */
    private ArrayList<Item> items = new ArrayList<Item>();

    public Inventory() {
        this.items = null;
}
    public UUID addItem(Item item){
        /*
         * Adds item to the inventory
         * */
        items.add(item);
        return item.getID();
    }

    public boolean removeItem(UUID id){
        /*
         * Removes item from inventory
         * */
        int counter = 0;
        for (int i = 0; i < this.items.size(); i++){
            if (items.get(i).getID() == id ){
                items.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean held(Item item){
        if (items.contains(item)){
            item.setHeld();
        }
        else {
            return false;
        }
        return true;
    }

    public int getItemcount(){
        int counter = 0;
        for (int i = 0; i < this.items.size(); i++){
            counter = counter + 1;
        }
        return counter;
    }

    public ArrayList<Item> getItems(){
        /*
         * Returns inventory contents
         * */
        return items;
    }
}
