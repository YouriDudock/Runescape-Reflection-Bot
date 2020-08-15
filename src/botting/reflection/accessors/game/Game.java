package botting.reflection.accessors.game;

import botting.reflection.accessors.deque.Deque;

import java.applet.Applet;

public interface Game {

    public Class<?> getClientClass();

    public Object getClientInstance();

    public Class<?> getMainClass();

    public Object getMainInstance();

    public Class<?> getGameRendererClass();

    public Class<?> getRSInterfacesClass();

    public Class<?> getRightMenuClass();

    public Deque getRightMenuDequeInstance();

    public Class<?> getMenuNode();

    public Class<?> getClientDetailsClass();

    public Applet getApplet();

    public void main(String[] args);
}
