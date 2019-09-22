package fr.feavy.simpleDB2;

import fr.feavy.simpleDB2.metadata.Arg;
import fr.feavy.simpleDB2.metadata.Save;
import fr.feavy.simpleDB2.sql.SQLType;

public class Item {
    @Save(primary = true)
    private final int id = 0;

    @Save(args = {
            @Arg(type = SQLType.DataType.NUMBER, value = "16")
    })
    private final String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
