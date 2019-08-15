package fr.feavy.simpleDB;

import com.sun.istack.internal.Nullable;
import oracle.jdbc.OracleDriver;

import javax.swing.plaf.nimbus.State;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class DatabaseManager {
    private static DatabaseManager instance;

    private Connection connection;

    private Map<Class, Table> tables = new HashMap<>();

    private DatabaseManager(String url, String username, String password) throws SQLException {
        DriverManager.registerDriver(new OracleDriver());
        this.connection = DriverManager.getConnection(url, username, password);
    }

    public static void createInstance(String url, String username, String password) throws SQLException {
        instance = new DatabaseManager(url, username, password);
    }

    @Nullable
    public static DatabaseManager getInstance() {
        return instance;
    }

    public boolean tableExists(String name) {
        try {
            Statement st = connection.createStatement();
            ResultSet rep = st.executeQuery("SELECT COUNT(*) " +
                    "FROM USER_TABLES " +
                    "WHERE TABLE_NAME = '" + name + "'");
            rep.next();
            return rep.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createTableFromClass(Class c) throws SQLException {
        Save s;
        short primaryAmount = 0;
        String varName;
        String typeName;

        StringBuilder req = new StringBuilder();
        req.append("CREATE TABLE " + c.getSimpleName().toUpperCase() + " (");

        Field[] fields = c.getDeclaredFields();
        Field lastField = fields[fields.length - 1];
        for (Field f : fields) {
            if (f.isAnnotationPresent(Save.class)) {     // Cet attribut doit être sauvegardé
                s = (Save) f.getAnnotation(Save.class);

                varName = f.getName();
                typeName = f.getType().getName();

                if (s.primary()) {
                    primaryAmount++;
                    req.append(varName + " " + s.type());
                } else {
                    req.append(varName + " " + s.type());
                }

                if (f != lastField)
                    req.append(", ");
            }
        }
        req.append(")");

        Statement st = connection.createStatement();
        st.execute(req.toString());
        st.execute("CREATE SEQUENCE seq_" + c.getSimpleName().toUpperCase() + " START WITH 1");
    }

    private void removeColumnFromTable(String name, String columnName) throws SQLException {
        Statement st = connection.createStatement();
        st.execute("ALTER TABLE "+name+" DROP COLUMN "+columnName);
    }

    private void addColumnToTable(String name, String columnName, Save type) throws SQLException {
        Statement st = connection.createStatement();
        st.execute("ALTER TABLE "+name+" ADD "+columnName+" "+type.type());
    }

    public void saveObject(Object obj) throws Exception {
        String name = getTableName(obj.getClass());

        if (!tableExists(name)) {
            createTableFromClass(obj.getClass());
        }else if(!tables.containsKey(obj.getClass())) {
            Table dbTable = getDatabaseTable(name);
            ClassTable objTable = getClassTable(obj.getClass());

            if(!dbTable.equals(objTable)) {
                List<String> diff = dbTable.minusColumns(objTable);
                for(String s : diff)
                    removeColumnFromTable(name, s);

                diff = objTable.minusColumns(dbTable);
                for(String s : diff)
                    addColumnToTable(name, s, objTable.getAnnotation(s));
            }
            tables.put(obj.getClass(), objTable);
        }

        String tblName = getTableName(obj.getClass());

        String names = "";
        String values = "";

        Field[] fields = obj.getClass().getDeclaredFields();
        Field lastField = fields[fields.length - 1];

        for (Field f : fields) {
            if (f.isAnnotationPresent(Save.class)) {     // Cet attribut doit être sauvegardé
                Save s = (Save) f.getAnnotation(Save.class);

                names += f.getName();

                if (s.primary())
                    values += "seq_" + tblName + ".nextval";
                else if (!f.isAccessible()) {
                    f.setAccessible(true);
                    values += DataFormatter.getFormatter(f.getType()).formatValue(f.get(obj));
                    f.setAccessible(false);
                } else
                    values += DataFormatter.getFormatter(f.getType()).formatValue(f.get(obj));

                if (f != lastField) {
                    names += ", ";
                    values += ", ";
                }
            }
        }

        Statement st = connection.createStatement();
        st.execute("INSERT INTO " + tblName + "(" + names + ") VALUES(" + values + ")");
    }

    private String getTableName(Class c) {
        return c.getSimpleName().toUpperCase();
    }

    private Table getDatabaseTable(String name) throws SQLException {
        List<String> columnsName = new ArrayList<>();

        Statement st = connection.createStatement();
        ResultSet set = st.executeQuery("SELECT TABLE_NAME, COLUMN_NAME " +
                                             "FROM USER_TAB_COLUMNS " +
                                             "WHERE table_name = '"+name+"'");

        while(set.next())
            columnsName.add(set.getString("COLUMN_NAME"));

        return new Table(name, columnsName);
    }

    private ClassTable getClassTable(Class c) {
        String name = getTableName(c);
        List<String> columnsName = new ArrayList<>();
        Map<String, Save> annotations = new HashMap<>();

        Field[] fields = c.getDeclaredFields();

        for (Field f : fields)
            if (f.isAnnotationPresent(Save.class)) {
                columnsName.add(f.getName().toUpperCase());
                annotations.put(f.getName().toUpperCase(), (Save)f.getAnnotation(Save.class));
            }

        return new ClassTable(name, columnsName, annotations);
    }
}
