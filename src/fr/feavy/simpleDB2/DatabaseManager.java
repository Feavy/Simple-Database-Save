package fr.feavy.simpleDB2;

import fr.feavy.simpleDB2.sql.SQLBuilder;
import fr.feavy.simpleDB2.structure.Column;
import fr.feavy.simpleDB2.structure.Row;
import fr.feavy.simpleDB2.structure.Table;
import fr.feavy.simpleDB2.utils.DataFormatter;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private Connection connection;

    private static DatabaseManager instance;

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
        String query = "SELECT COUNT(*) FROM USER_TABLES WHERE TABLE_NAME='"+name+"'";
        Statement st = connection.createStatement();
        ResultSet rep = st.executeQuery(query);
        return rep.getInt(1) == 1;
    }

    private void createTable(Table table) throws SQLException {
        Statement st = connection.createStatement();
        st.execute(SQLBuilder.buildCreateTableQuery(table));
    }

    private Table getTable(String name) {
        return null;
    }

    public void save(Object obj) {

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