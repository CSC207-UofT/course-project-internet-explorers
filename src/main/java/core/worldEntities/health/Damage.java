package core.worldEntities.health;
import java.util.UUID;

// record used to include info such as source, type, etc. in the future
public record Damage(float amount, UUID sourceID) {}
