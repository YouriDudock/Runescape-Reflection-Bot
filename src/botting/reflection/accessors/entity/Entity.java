package botting.reflection.accessors.entity;

public interface Entity {

    public int getX();

    public int getY();

    public int getHeight();

    public int getHealth();

    public int getInteractingID();

    public void setInteractingID(int ID);

    public int getAnimation();

    public int getLoopCycleStatus();
}
