package core.InventorySystem;

public interface ItemUsageDelegate {
    public boolean hold(Item item);
    public void use(Item item);
}
