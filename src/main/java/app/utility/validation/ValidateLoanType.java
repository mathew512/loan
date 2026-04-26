package app.utility.validation;

import jakarta.enterprise.context.ApplicationScoped;
import app.model.LoanType;

@ValidatorQualifier(ValidatorQualifier.ValidationChoice.LOAN_TYPE)
@ApplicationScoped
public class ValidateLoanType implements Validate<LoanType> {

    @Override
    public boolean isValid(LoanType loanType) {
        // Example validation: name must be at least 3 chars
        return loanType != null &&
               loanType.getName() != null &&
               loanType.getName().trim().length() >= 3;
    }
}
