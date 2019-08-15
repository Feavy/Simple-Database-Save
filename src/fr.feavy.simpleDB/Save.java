package fr.feavy.simpleDB;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Save {
    boolean primary() default false;
    String type();
}
