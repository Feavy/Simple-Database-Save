package fr.feavy.simpleDB2;

import fr.feavy.simpleDB2.metadata.Arg;
import fr.feavy.simpleDB2.metadata.Save;
import fr.feavy.simpleDB2.sql.SQLType;

public class Player {
    @Save(primary = true, args = {
            @Arg(type = SQLType.DataType.NUMBER, value = "5")
    })
    private int id = 0;

    @Save(args = {
            @Arg(type = SQLType.DataType.NUMBER, value = "6")
    })
    private int health = 10;

    @Save(args = {
            @Arg(type = SQLType.DataType.NUMBER, value = "16")
    })
    private String name = "Undefined";

    //@Save
    //private Item heldItem;

    /*
    Constructeur par défaut obligatoire (si d'autres constructeurs existent) :
     */

    public Player() {
    }

    public Player(String name, int health) {
        this.name = name;
        this.health = health;
    }
}
