package botting.game.data;


/**
 * A skill in the game
 *
 * @author Youri Dudock
 */
public class GameSkill {

    // id of the skill declared in the 317 protocol
    private int id;

    public GameSkill(int id) {
        this.id = id;
    }


    public int getID() {
        return id;
    }
}