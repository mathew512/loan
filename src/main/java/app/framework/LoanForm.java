package app.framework;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LoanForm {
    String label();
    String actionUrl();
    String method() default "POST";
}
