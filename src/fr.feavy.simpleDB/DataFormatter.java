package fr.feavy.simpleDB;

import java.util.HashMap;
import java.util.Map;

public abstract class DataFormatter<E> {
    private static Map<String, DataFormatter> formatters = new HashMap<>();
    static  {
        formatters.put(String.class.getName(), new DataFormatter<String>() {
            @Override
            protected String formatValue(String value) {
                return "'"+value+"'";
            }
        });

        formatters.put("boolean", new DataFormatter<Boolean>() {
            @Override
            protected String formatValue(Boolean value) {
                return value ? "1" : "0";
            }
        });
    }

    public static DataFormatter getFormatter(Class c) {
        if(formatters.containsKey(c.getName()))
            return formatters.get(c.getName());
        else
            return getDefaultFormatter();
    }

    public static DataFormatter getFormatterFor(Object obj) {
        return getFormatter(obj.getClass());
    }

    public static DataFormatter getDefaultFormatter() {
        return new DataFormatter() {
            @Override
            protected String formatValue(Object value) {
                return value.toString();
            }
        };
    }

    protected abstract String formatValue(E value);
}
