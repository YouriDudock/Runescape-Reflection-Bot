package botting.reflection;

import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * A class loader wrapper for easy access
 *
 * @author Youri Dudock
 */
public class ClassLoader {

    private java.lang.ClassLoader classLoader;


    public java.lang.ClassLoader getClassLoader() {
        return classLoader;
    }

    public Class<?> loadClass(String name) {
        try {
            return classLoader.loadClass(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void createClassLoader(String JAR) {
        try {

            if (classLoader != null) {
                Debugger.write(ClassLoader.class, "Class loader already exists!", DebugPriority.HIGH);
            }

            URL[] urls = new URL[]{new File(JAR).toURI().toURL()};
            classLoader = new URLClassLoader(urls);

            Debugger.write(ClassLoader.class, "Class loader created:" + JAR, DebugPriority.HIGH);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
