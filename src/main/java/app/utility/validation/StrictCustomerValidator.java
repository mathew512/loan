package app.utility.validation;

import jakarta.enterprise.inject.Alternative;
import app.model.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

@Alternative
@ApplicationScoped
@ValidatorQualifier(ValidatorQualifier.ValidationChoice.CUSTOMER)
public class StrictCustomerValidator implements Validate<Customer> {

    @Override
    public boolean isValid(Customer c) {
        return c != null
            && c.getNationalId() != null
            && c.getNationalId().length() >= 8;
    }
}