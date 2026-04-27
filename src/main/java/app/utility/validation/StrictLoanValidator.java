package app.utility.validation;

import jakarta.enterprise.inject.Alternative;
import app.model.LoanType;
import jakarta.enterprise.context.ApplicationScoped;

@Alternative
@ApplicationScoped
@ValidatorQualifier(ValidatorQualifier.ValidationChoice.LOAN_TYPE)
public class StrictLoanValidator implements Validate<LoanType> {

    @Override
    public boolean isValid(LoanType t) {
        return t != null && 
               t.getMaxAmount() !=null &&
               t.getMaxAmount().doubleValue() > 1000;
    }
}