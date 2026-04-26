package app.utility.validation;

import jakarta.inject.Qualifier;
import java.lang.annotation.*;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface ValidatorQualifier {

    ValidationChoice value();

    enum ValidationChoice {
        CUSTOMER,
        LOAN,
        LOAN_TYPE,
        REPAYMENT
    }
}