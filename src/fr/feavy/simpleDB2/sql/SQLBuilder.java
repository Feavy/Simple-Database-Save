package fr.feavy.simpleDB2.sql;

import fr.feavy.simpleDB2.structure.Column;
import fr.feavy.simpleDB2.structure.Table;

import java.util.List;

public class SQLBuilder {
    public static String buildCreateTableQuery(Table table) {
        final List<Column> columns = table.getColumns();
        Column last = columns.get(columns.size()-1);

        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE "+table.getName()+" (");

        for(Column column : columns) {
            query.append(column);
            if(column != last)
                query.append(", ");
        }
        query.append(")");
        return query.toString();
    }
}
