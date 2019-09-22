package fr.feavy.simpleDB2;

import fr.feavy.simpleDB2.sql.SQLBuilder;
import fr.feavy.simpleDB2.structure.Table;
import fr.feavy.simpleDB2.utils.DBUtils;
import oracle.jdbc.OracleDriver;

import javax.xml.crypto.Data;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DriverManager.registerDriver(new OracleDriver());
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "test", "admin");
        ResultSet rep = connection.createStatement().executeQuery("SELECT * FROM PLAYER");
        ResultSetMetaData meta = rep.getMetaData();

        /*for(int i = 0; i < meta.getColumnCount(); i++) {
            System.out.println(meta.getColumnLabel(i+1)+" "+meta.isNullable(i+1)+" "+meta.de(i+1));
        }*/


        Table playerTbl = Table.fromClass(Player.class);
        System.out.println(playerTbl);

        Table playerDBTbl = Table.fromDatabase(DBUtils.classNameToTableName(Player.class), connection);
        System.out.println(playerDBTbl);

        DatabaseManager.createInstance(connection);
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Player player = new Player("Jean" , 20);
        databaseManager.save(player);
        //System.out.println(SQLBuilder.buildCreateTableQuery(playerTbl));
    }
}
