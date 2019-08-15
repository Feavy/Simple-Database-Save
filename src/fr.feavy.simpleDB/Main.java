package fr.feavy.simpleDB;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        DatabaseManager.createInstance("jdbc:oracle:thin:@localhost:1521:xe", "test", "admin");

        Player p = new Player("Jean Bon", 15, 4500, true);
        DatabaseManager.getInstance().saveObject(p);
        //DatabaseManager.getInstance().createTableFromClass(Player.class);
    }
}
