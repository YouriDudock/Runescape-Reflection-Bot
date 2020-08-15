package botting.reflection.accessors.io;

import java.awt.event.MouseEvent;

public interface Mouse {

    public int getX();

    public int getY();

    public void setX(int x);

    public void setY(int y);

    public void scrollMouse(MouseEvent event);

    public void mousePressed(MouseEvent event);

    public void mouseReleased(MouseEvent event);
}
