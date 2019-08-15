package fr.feavy.simpleDB2;

import fr.feavy.simpleDB2.saver.Arg;
import fr.feavy.simpleDB2.saver.Save;
import fr.feavy.simpleDB2.sql.SQLType;

public class Player {
    @Save(primary = true, args = {
            @Arg(type = SQLType.Type.NUMBER, value = "5")
    })
    private int id = -1;

    @Save(args = {
            @Arg(type = SQLType.Type.NUMBER, value = "6")
    })
    private int health = 10;

    @Save(args = {
            @Arg(type = SQLType.Type.NUMBER, value = "16")
    })
    private String name = "Undefined";

    /*
    Constructeur par défaut obligatoire (si d'autres constructeurs existent) :
     */

    public Player() {
    }

    public Player(int id, String name, int health) {
        this.id = id;
        this.name = name;
        this.health = health;
    }
}
