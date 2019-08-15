package fr.feavy.simpleDB2.saver;

import fr.feavy.simpleDB2.sql.SQLType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Arg {
    SQLType.Type type();
    String value();
}
