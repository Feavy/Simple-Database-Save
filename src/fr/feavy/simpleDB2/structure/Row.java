package fr.feavy.simpleDB2.structure;

import java.util.Map;

public class Row {
    private Table table;
    private String id;
    private Map<Column, String> values;

    public Row(Table table, String id, Map<Column, String> values) {
        this.table = table;
        this.id = id;
        this.values = values;
    }

    public Table getTable() {
        return table;
    }

    public String getId() {
        return id;
    }

    public Map<Column, String> getValues() {
        return values;
    }
}
