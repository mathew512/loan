package app.utility.validation;

import jakarta.inject.Qualifier;
import java.lang.annotation.*;

/**
 * Custom CDI qualifier to distinguish between different validator beans.
 * Can be applied to fields, constructors, and setter methods.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface ValidatorQualifier {

    ValidationChoice value();

    enum ValidationChoice {
        CUSTOMER,
        LOAN,
        LOAN_TYPE,
        REPAYMENT
    }
}
