package fr.feavy.simpleDB2.sql;

import fr.feavy.simpleDB2.saver.Arg;
import fr.feavy.simpleDB2.utils.DataFormatter;

import java.lang.annotation.Annotation;

public class SQLType {
    private Type type;
    private Arg[] args;

    public SQLType(Type type, Arg[] args) {
        this.type = type;
        this.args = args;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SQLType) {
            SQLType other = (SQLType)obj;
            if(type == other.type && args.length == other.args.length) {
                for(int i = 0; i < args.length; i++) {
                    if(args[i].type() != other.args[i].type() || !args[i].value().equals(other.args[i].value()))
                        return false;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder rep = new StringBuilder();
        rep.append(type);
        if(args.length > 0) {
            rep.append("(");
            rep.append(DataFormatter.getFormatter(args[0].getClass()).formatValue(args[0].value()));
            for(int i = 1; i < args.length; i++) {
                rep.append(", "+DataFormatter.getFormatter(args[i].getClass()).formatValue(args[i].value()));
            }
            rep.append(")");
        }
        return rep.toString();
    }

    public static SQLType fromClass(Class c) throws Exception {
        return fromClass(c, new Arg[0]);
    }

    public static SQLType fromClass(Class c, Arg[] args) throws Exception {
        Type type;
        switch(c.getName()) {
            case "int":
                type = Type.NUMBER;
                break;
            case "boolean":
                type = Type.NUMBER;
                args = new Arg[]{new Arg(){
                    @Override
                    public String value() {
                        return "1";
                    }

                    @Override
                    public Type type() {
                        return Type.NUMBER;
                    }

                    @Override
                    public Class<? extends Annotation> annotationType() {
                        return Arg.class;
                    }
                }};
                break;
            case "java.lang.String":
                type = Type.VARCHAR;
                break;
            default:
                throw new Exception("Le type : "+c.getName()+ " n'est pas pris en charge.");
        }
        return new SQLType(type, args);
    }

    public enum Type {
        NUMBER, VARCHAR;
    }
}
