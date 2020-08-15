package botting.reflection.accessors.deque;

/**
 * @author Youri Dudock
 */
public interface Node {

    public Node getNext();

    public String getBaseMenuItemName();

    public String getExtraBaseMenuItemName();

    public Object getAccessor();


}
