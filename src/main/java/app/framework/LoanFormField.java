package app.framework;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LoanFormField {
    String label();
    String placeholder() default "";
    String name() default "";
}
