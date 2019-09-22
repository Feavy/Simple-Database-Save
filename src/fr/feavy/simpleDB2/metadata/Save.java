package fr.feavy.simpleDB2.metadata;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Save {
    boolean primary() default false;
    boolean nullable() default false;
    Arg[] args() default {};
}
