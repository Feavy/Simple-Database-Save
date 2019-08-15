package fr.feavy.simpleDB;

import java.util.List;
import java.util.Map;

public class ClassTable extends Table {
    private Map<String, Save> annotations;

    public ClassTable(String name, List<String> columnsName, Map<String, Save> annotations) {
        super(name, columnsName);
        this.annotations = annotations;
    }

    public Map<String, Save> getAnnotations() {
        return annotations;
    }

    public Save getAnnotation(String columnName){
        return annotations.get(columnName);
    }
}
