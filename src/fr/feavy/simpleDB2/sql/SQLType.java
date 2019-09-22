package fr.feavy.simpleDB2.sql;

import fr.feavy.simpleDB2.metadata.Arg;
import fr.feavy.simpleDB2.utils.DataFormatter;

import javax.xml.crypto.Data;
import java.lang.annotation.Annotation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLType {
    private DataType dataType;
    private Arg[] args;

    public SQLType(DataType dataType, Arg[] args) {
        this.dataType = dataType;
        this.args = args;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SQLType) {
            SQLType other = (SQLType)obj;
            if(dataType == other.dataType && args.length == other.args.length) {
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
        rep.append(dataType);
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

    public static SQLType fromResultSet(ResultSet rep) throws SQLException {
        String dataTypeStr = rep.getString("DATA_TYPE");
        DataType type = null;
        List<Arg> args = new ArrayList<>();
        if(dataTypeStr.contains("VARCHAR")) {
            type = DataType.VARCHAR;
            args.add(createArg(rep.getInt("DATA_LENGTH")+"", DataType.NUMBER));
        }else if(dataTypeStr.equals("NUMBER")) {
            type = DataType.NUMBER;
            args.add(createArg(rep.getInt("DATA_PRECISION")+"", DataType.NUMBER));
            int scale = rep.getInt("DATA_SCALE");
            if(scale != 0)
                args.add(createArg(scale+"", DataType.NUMBER));
        }
        return new SQLType(type, args.toArray(new Arg[0]));
    }

    public static SQLType fromClass(Class c) throws Exception {
        return fromClass(c, new Arg[0]);
    }

    public static SQLType fromClass(Class c, Arg[] args) throws Exception {
        DataType dataType;
        switch(c.getName()) {
            case "int":
                dataType = DataType.NUMBER;
                break;
            case "boolean":
                dataType = DataType.NUMBER;
                args = new Arg[]{createArg("1", DataType.NUMBER)};
                break;
            case "java.lang.String":
                dataType = DataType.VARCHAR;
                break;
            default:
                throw new Exception("Le type : "+c.getName()+ " n'est pas pris en charge.");
        }
        return new SQLType(dataType, args);
    }

    private static Arg createArg(String value, DataType type) {
        return new Arg(){
            @Override
            public String value() {
                return value;
            }

            @Override
            public DataType type() {
                return type;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Arg.class;
            }
        };
    }

    public enum DataType {
        NUMBER, VARCHAR;
    }
}
