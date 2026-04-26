package app.utility.validation;

import jakarta.enterprise.context.ApplicationScoped;
import app.model.Customer;

@ValidatorQualifier(ValidatorQualifier.ValidationChoice.CUSTOMER)
@ApplicationScoped
public class ValidateCustomer implements Validate<Customer> {

    @Override
    public boolean isValid(Customer customer) {
        return customer != null &&
               customer.getFullName() != null &&
               customer.getFullName().trim().length() >= 3;
    }
}
