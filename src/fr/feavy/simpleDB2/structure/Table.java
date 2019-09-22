package fr.feavy.simpleDB2.structure;

import com.sun.istack.internal.Nullable;
import fr.feavy.simpleDB2.utils.DBUtils;
import fr.feavy.simpleDB2.utils.DataFormatter;
import fr.feavy.simpleDB2.sql.SQLType;
import fr.feavy.simpleDB2.metadata.Save;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private String name;
    private List<Column> columns;

    public Table(String name, List<Column> columns) throws Exception {
        this.name = name;
        this.columns = columns;
        int primaryAttrAmount = 0;
        for (Column c : columns) {
            c.setTable(this);
            if (c.isPrimary())
                primaryAttrAmount++;
        }
        if (primaryAttrAmount != 1)
            throw new Exception("Le nombre de clés primaires de la tables est différent de 1.");
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Column> minus(Table other) {
        List<Column> result = new ArrayList<>();
        for(Column c : columns) {
            if(!other.columns.contains(c))
                result.add(c);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder rep = new StringBuilder();
        rep.append("-= "+getName()+" =-\n");
        for(Column c : columns)
            rep.append(c+"\n");
        return rep.toString();
    }

    @Nullable
    public static Table fromClass(Class c) {
        String name = DBUtils.classNameToTableName(c);
        List<Column> columns = new ArrayList<>();

        Field[] fields = c.getDeclaredFields();

        try {
            Object o = c.getConstructor(null).newInstance();

            for (Field f : fields) {
                if (f.isAnnotationPresent(Save.class)) {
                    Save s = (Save) f.getAnnotation(Save.class);

                    SQLType type = SQLType.fromClass(f.getType(), s.args());
                    String varName = f.getName();
                    String defaultValue;
                    try {
                        defaultValue = DataFormatter.getFormatterFor(f.get(o)).formatValue(f.get(o));
                    }catch(Exception e) {
                        f.setAccessible(true);
                        defaultValue = DataFormatter.getFormatterFor(f.get(o)).formatValue(f.get(o));
                        f.setAccessible(false);
                    }

                    columns.add(new Column(DBUtils.varNameToColumnName(f.getName()), type, defaultValue, s.primary(), s.nullable()));
                }
            }

            return new Table(name, columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Table fromDatabase(String name, Connection connection) throws Exception {
        List<Column> columns = new ArrayList<>();

        String query = "SELECT COLUMN_NAME, DATA_TYPE, DATA_LENGTH, DATA_PRECISION, DATA_SCALE, NULLABLE, DATA_DEFAULT" +
                " FROM all_tab_columns WHERE TABLE_NAME='"+name+"'";
        String primaryColumnQuery = "SELECT cols.column_name " +
                "FROM user_constraints cons, user_cons_columns cols " +
                "WHERE cols.table_name = '"+name+"' " +
                "AND cons.constraint_type = 'P' " +
                "AND cons.constraint_name = cols.constraint_name";

        Statement st = connection.createStatement();

        ResultSet rep = st.executeQuery(primaryColumnQuery);
        rep.next();
        String primaryColumnName = rep.getString("COLUMN_NAME");

        rep = st.executeQuery(query);

        while(rep.next()) {
            String columnName = rep.getString("COLUMN_NAME");

            columns.add(new Column(columnName, SQLType.fromResultSet(rep),
                                    rep.getString("DATA_DEFAULT"),
                                    columnName.equals(primaryColumnName), rep.getString("NULLABLE").equals("Y")));
        }
        return new Table(name, columns);
    }
}
