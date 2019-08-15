package fr.feavy.simpleDB2.utils;

public class DBUtils {
    public static String classNameToTableName(Class c) {
        return c.getSimpleName().toUpperCase();
    }
    public static String varNameToColumnName(String name) {
        return name.toUpperCase();
    }
}
