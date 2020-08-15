package botting.game.entity;

/**
 * The cache definition of a NPC
 *
 * @author Youri Dudock
 */
public class GameNPCDefinition {

    private Object instance;

    public GameNPCDefinition(Object instance) {
        this.instance = instance;
    }

    public Object getInstance() {
        return instance;
    }
}
