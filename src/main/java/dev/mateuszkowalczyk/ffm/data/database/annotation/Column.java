package dev.mateuszkowalczyk.ffm.data.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    public enum Type {
        STRING, INT, DATE
    }

    public String name() default "";
    public Type type() default Type.STRING;
}
