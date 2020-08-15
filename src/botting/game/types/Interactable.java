package botting.game.types;

/**
 * In game objects that can be interacted with (npcs, ground items, etc)
 *
 * @author Youri Dudock
 */
public interface Interactable {

    public boolean interact(String option);

    public boolean findAndInteract(String option);
}
