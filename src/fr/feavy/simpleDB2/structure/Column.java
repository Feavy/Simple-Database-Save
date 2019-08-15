package fr.feavy.simpleDB2.structure;

import fr.feavy.simpleDB2.sql.SQLType;

public class Column {
    private Table table;
    private String name;
    private SQLType type;
    private String defaultValue;
    private boolean isPrimary;
    private boolean isNullable;

    public Column(String name, SQLType type, String defaultValue, boolean isPrimary, boolean isNullable) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
        this.isPrimary = isPrimary;
        this.isNullable = isNullable;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    public String getName() {
        return name;
    }

    public SQLType getType() {
        return type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public boolean isNullable() {
        return isNullable;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Column) {
            Column other = (Column)obj;
            return (table.getName() == other.table.getName()
                    && name.equals(other.name)
                    && type.equals(other.type)
                    && defaultValue.equals(other.defaultValue)
                    && isPrimary == other.isPrimary
                    && isNullable == other.isNullable);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder rep = new StringBuilder();
        rep.append(name+" "+type);
        if(isPrimary)
            rep.append(" PRIMARY_KEY");
        if(defaultValue != null)
            rep.append(" DEFAULT "+defaultValue);
        if(isNullable)
            rep.append(" NOT NULL");
        return rep.toString();
    }
}
