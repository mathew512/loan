package app.utility.validation;

import jakarta.enterprise.context.ApplicationScoped;
import app.model.Repayment;
import java.math.BigDecimal;
import java.util.Date;

@ValidatorQualifier(ValidatorQualifier.ValidationChoice.REPAYMENT)
@ApplicationScoped
public class ValidateRepayment implements Validate<Repayment> {

    @Override
    public boolean isValid(Repayment repayment) {
        return repayment != null &&
               repayment.getLoanId() > 0 &&
               repayment.getAmountPaid() != null &&
               repayment.getAmountPaid().compareTo(BigDecimal.ZERO) > 0 &&
               repayment.getPaymentDate() != null;
    }
}
