package fr.feavy.simpleDB2;

import fr.feavy.simpleDB2.sql.SQLBuilder;
import fr.feavy.simpleDB2.structure.Table;
import oracle.jdbc.OracleDriver;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        DriverManager.registerDriver(new OracleDriver());
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "test", "admin");
        ResultSet rep = connection.createStatement().executeQuery("SELECT * FROM PLAYER");
        ResultSetMetaData meta = rep.getMetaData();

        for(int i = 0; i < meta.getColumnCount(); i++) {
            System.out.println(meta.getColumnLabel(i+1)+" "+meta.isNullable(i+1)+" "+meta.de(i+1));
        }

        Table playerTbl = Table.fromClass(Player.class);
        System.out.println(playerTbl);
        //System.out.println(SQLBuilder.buildCreateTableQuery(playerTbl));
    }
}
