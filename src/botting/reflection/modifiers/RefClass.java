package botting.reflection.modifiers;

import botting.bot.debug.DebugPriority;
import botting.bot.debug.Debugger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A <code>RefClass</code> represents a class or an instance of that class, if
 * no instance is provided this class can only get values from static fields and
 * only invoke static methods
 *
 * @author Everel
 */
public class RefClass extends botting.reflection.modifiers.RefModifiers {
    private Object   instance;
    private Class<?> clazz;

    public RefClass(Class<?> clazz) {
        this(clazz, null);
    }

    public RefClass(Object instance) {
        this(instance.getClass(), instance);
    }

    public RefClass(Class<?> clazz, Object instance) {
        super(clazz.getModifiers());
        this.clazz = clazz;
        setInstance(instance);
    }

    /**
     * Gets the instance of this class
     *
     * @return if an instance of this class is known it will return that
     * instance, otherwise it will return null.
     */
    public Object getInstance() {
        return this.instance;
    }

    /**
     * Sets the instance of this class so now non static fields values can be
     * retrieved and non static methods can be invoked
     *
     * @param instance instance of this class.
     */
    public void setInstance(Object instance) {
        if (instance == null) {
            this.instance = null;
            return;
        }
        if (this.clazz != null) {
            if (!clazz.isInstance(instance)) {
                throw new IllegalArgumentException(instance
                        + " is not an instance of the class " + clazz);
            }
        }
        this.instance = instance;
    }

    /**
     * Gets the class which this RefClass is representing
     *
     * @return class which this RefClass is representing
     */
    public Class<?> getRepresentingClass() {
        return this.clazz;
    }

    public String getClassName() {
        return this.clazz.getName();
    }

    public String getSimpleName() {
        return this.clazz.getSimpleName();
    }

    public String getCanonicalName() {
        return this.clazz.getCanonicalName();
    }

    public Annotation[] getAnnotations() {
        return this.clazz.getAnnotations();
    }

//    /**
//     * Gets the type of this class
//     *
//     * @return type of this class
//     */
//    public botting.objectweb.asm.Type getASMType() {
//        return botting.objectweb.asm.Type.getType(this.clazz);
//    }

    /**
     * Gets the class' fields
     *
     * @return getNearest fields if an instance is provided, otherwise only static
     * fields
     */
    public botting.reflection.modifiers.RefField[] getFields() {
        ArrayList<botting.reflection.modifiers.RefField> fields = new ArrayList<botting.reflection.modifiers.RefField>();
        // add getNearest static fields
        for (Field f : clazz.getDeclaredFields()) {
            if (Modifier.isStatic(f.getModifiers())) {
                fields.add(new botting.reflection.modifiers.RefField(f, instance));
            }
        }
        if (this.instance != null) {
            // add getNearest non static fields
            for (Field f : clazz.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers())) {
                    fields.add(new botting.reflection.modifiers.RefField(f, instance));
                }
            }
        }
        return fields.toArray(new botting.reflection.modifiers.RefField[fields.size()]);
    }

    /**
     * Determines if a object is an instance of this class
     *
     * @param object the object you want to check
     *
     * @return <code>true</code> if the object is an instance of this class; <code>false</code> otherwise
     */
    public boolean instanceOf(Object object) {
        return this.clazz.isInstance(object);
    }

    /**
     * Gets field by field name
     *
     * @param name name of the field
     *
     * @return the field if found
     */
    public botting.reflection.modifiers.RefField getField(String name) {
        return getField(name, null);
    }

    /**
     * Gets field by field name and desc
     *
     * @param name name of the field
     * @param desc desc type of the field
     *
     * @return the field if found
     */
    public botting.reflection.modifiers.RefField getField(String name, String desc) {
        botting.reflection.modifiers.RefField[] fields = getFields();
        for (botting.reflection.modifiers.RefField f : fields) {
            if (f.getName().equals(name)) {
                if (desc == null) {
                    return f;
                }
                if (desc.equals(f.getTypeDesc())) {
                    return f;
                }
            }
        }


        Debugger.write(getClass(), "Field returned null. Class:" + getRepresentingClass(), DebugPriority.HIGH);
        return null;
    }

    /**
     * Determines if this class has a super class
     *
     * @return <code>true</code> if this class has a super class and which is
     * not the java/lang/Object class, otherwise <code>false.</code>
     */
    public boolean hasSuperclass() {
        return hasSuperclass(true);
    }

    /**
     * Determines if this class has a super class
     *
     * @param ignoreObjectClass if you want this method to return false when the superclass is
     *                          the java/lang/Object class
     *
     * @return <code>true</code> if this class has a superclass, otherwise
     * <code>false</code>
     */
    public boolean hasSuperclass(boolean ignoreObjectClass) {
        if (!ignoreObjectClass) {
            return !clazz.equals(Object.class);
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null) {
            return false;
        }
        return !superClass.equals(Object.class);
    }

    /**
     * Returns a new RefClass representing the superclass of this RefClass
     *
     * @return superclass of this RefClass
     */
    public RefClass getSuperclass() {
        return new RefClass(clazz.getSuperclass(), instance);
    }

    /**
     * Creates a new instance of this class
     *
     * @return a RefClass representing a fresh created instance of that class
     */
    public RefClass newInstance() {
        try {
            return new RefClass(clazz.newInstance());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the empty (without parameters) constructor of this class if any
     *
     * @return empty constructor if there, otherwise <code>null</code>
     */
    public botting.reflection.modifiers.RefConstructor getConstructor() {
        return getConstructor(new Class<?>[]{});
    }

    /**
     * Gets a RefConstructor from this class
     *
     * @param parameters the constructor it's parameters
     *
     * @return the retrieved constructor
     */
    public botting.reflection.modifiers.RefConstructor getConstructor(Class<?>[] parameters) {
        try {
            return new botting.reflection.modifiers.RefConstructor(clazz.getDeclaredConstructor(parameters));
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    /**
     * Gets getNearest constructors of this class
     *
     * @return an array with getNearest the constructors in this class
     */
    public botting.reflection.modifiers.RefConstructor[] getConstructors() {
        Constructor<?>[] constructors    = clazz.getDeclaredConstructors();
        botting.reflection.modifiers.RefConstructor[] refConstructors = new botting.reflection.modifiers.RefConstructor[constructors.length];
        for (int i = 0; i < constructors.length; i++) {
            refConstructors[i] = new botting.reflection.modifiers.RefConstructor(constructors[i]);
        }
        return refConstructors;
    }

    /**
     * Gets the class' methods
     *
     * @return getNearest methods if an instance is provided, otherwise only static
     * methods
     */
    public botting.reflection.modifiers.RefMethod[] getMethods() {
        ArrayList<botting.reflection.modifiers.RefMethod> methods = new ArrayList<botting.reflection.modifiers.RefMethod>();
        // add getNearest static methods
        for (Method m : clazz.getDeclaredMethods()) {
            if (Modifier.isStatic(m.getModifiers())) {
                methods.add(new botting.reflection.modifiers.RefMethod(m, instance));
            }
        }
        if (this.instance != null) {
            // add getNearest non static methods
            for (Method m : clazz.getDeclaredMethods()) {
                if (!Modifier.isStatic(m.getModifiers())) {
                    methods.add(new botting.reflection.modifiers.RefMethod(m, instance));
                }
            }
        }
        return methods.toArray(new botting.reflection.modifiers.RefMethod[methods.size()]);
    }

    /**
     * Finds and returns the first RefMethod match with given method name
     *
     * @param name method its name
     *
     * @return the first match, or if not found <code>null</code>
     */
    public botting.reflection.modifiers.RefMethod getMethod(String name) {
        return getMethod(name, null);
    }

    /**
     * Finds a RefMethod in this RefClass
     *
     * @param name       the method its name
     * @param parameters the method its parameters
     *
     * @return the matched method or if not found null <code>null</code>
     */
    public botting.reflection.modifiers.RefMethod getMethod(String name, Class<?>... parameters) {
        try {
            for (botting.reflection.modifiers.RefMethod method : getMethods()) {
                if (method.getName().equals(name)) {
                    if (parameters == null || Arrays.equals(method.getParameterTypes(), parameters)) {
                        return method;
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public String toString() {
        if (this.instance != null) {
            return new StringBuilder().append(this.instance.toString())
                    .append(" : ").append(this.clazz.toString()).toString();
        }
        return this.clazz.toString();
    }

}
