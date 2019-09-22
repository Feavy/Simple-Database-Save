package fr.feavy.simpleDB2.metadata;

import fr.feavy.simpleDB2.sql.SQLType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Arg {
    SQLType.DataType type();
    String value();
}
