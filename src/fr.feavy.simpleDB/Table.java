package fr.feavy.simpleDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Table {
    private String name;
    private List<String> columnsName;

    public Table(String name, List<String> columnsName) {
        this.name = name;
        this.columnsName = columnsName;
        Collections.sort(columnsName);
    }

    public String getName() {
        return name;
    }

    public List<String> getColumnsName() {
        return columnsName;
    }

    @Override
    public String toString() {
        String rep = name + " { ";
        for(String name : columnsName)
            rep += name+" ";
        rep += "}";
        return rep;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Table) {
            Table other = (Table)obj;
            return name.equals(other.name) && columnsName.equals(other.columnsName);
        }
        return super.equals(obj);
    }

    public List<String> minusColumns(Table other) {
        List<String> result = new ArrayList<>();
        for(String name : columnsName) {
            if(!other.columnsName.contains(name))
                result.add(name);
        }
        return result;
    }

    /*
    Sauvegarde d'un objet :

    Si la table n'existe pas => CREATE

    Si la table n'a pas la même structure => ALTER

    Si l'id n'existe pas => INSERT
    Si l'id existe       => UPDATE

     */
}
