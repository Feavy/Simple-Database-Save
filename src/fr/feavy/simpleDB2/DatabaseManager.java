package fr.feavy.simpleDB2;

import fr.feavy.simpleDB2.sql.SQLBuilder;
import fr.feavy.simpleDB2.structure.Column;
import fr.feavy.simpleDB2.structure.Row;
import fr.feavy.simpleDB2.structure.Table;
import fr.feavy.simpleDB2.utils.DBUtils;
import fr.feavy.simpleDB2.utils.DataFormatter;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseManager {
    private Connection connection;

    private static DatabaseManager instance;

    private List<Class> alreadyTestedClasses = new ArrayList<>();

    private DatabaseManager(Connection connection) {
        this.connection = connection;
    }

    public static void createInstance(Connection connection) {
        instance = new DatabaseManager(connection);
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    private boolean tableExists(String name) throws SQLException {
        String query = "SELECT COUNT(*) FROM USER_TABLES WHERE TABLE_NAME='" + name + "'";
        Statement st = connection.createStatement();
        ResultSet rep = st.executeQuery(query);
        rep.next();
        return rep.getInt(1) == 1;
    }

    private void createTable(Table table) throws SQLException {
        Statement st = connection.createStatement();
        st.execute(SQLBuilder.buildCreateTableQuery(table));
    }

    private Table getTable(String name) throws Exception {
        return Table.fromDatabase(name, connection);
    }

    public void save(Object obj) throws Exception {
        String tableName = DBUtils.classNameToTableName(obj.getClass());
        if(!tableExists(tableName)) {
            createTable(Table.fromClass(obj.getClass()));
            alreadyTestedClasses.add(obj.getClass());
        } else if(!alreadyTestedClasses.contains(obj.getClass())){
            Table classTbl = Table.fromClass(obj.getClass());
            Table dbTbl = getTable(tableName);
            if(!classTbl.equals(dbTbl)) {
                System.err.println("Différence de structure !");
                List<Column> classMinusDb = classTbl.minus(dbTbl);
                List<Column> dbMinusClass = dbTbl.minus(classTbl);

                System.out.println("Class minus Database : ");
                System.out.println(Arrays.toString(classMinusDb.toArray(new Column[0])));

                System.out.println("Database minus Class : ");
                System.out.println(Arrays.toString(dbMinusClass.toArray(new Column[0])));
            }
        }

    }

    public Object read(Object obj) {
        return null;
    }

    public Object[] readAll(Class c) {
        return null;
    }

    private void insert(Row row) {

    }

    private void update(Row row) {

    }

    private void insertOrUpdate(Row row) {

    }

    private void addColumn(Column column) {

    }

    private void removeColumn(Column column) {

    }

    private void updateColumn(Column oldColumn, Column newColumn) {

    }
}