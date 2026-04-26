package app.framework;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LoanTable {
    String label();
    String tableUrl();
    String registerUrl();
    String formUrl();
}
