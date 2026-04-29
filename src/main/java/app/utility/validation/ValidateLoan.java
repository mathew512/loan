package app.utility.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.model.Loan;

@Named("loanValidator")
@ValidatorQualifier(ValidatorQualifier.ValidationChoice.LOAN)
@ApplicationScoped
public class ValidateLoan implements Validate<Loan> {

    @Inject
    @Named("loanDS")
    private DataSource ds;

    @Override
    public boolean isValid(Loan loan) {
        // Basic field checks
        boolean basicValid = loan != null &&
                             loan.getCustomerName() != null &&
                             loan.getCustomerName().trim().length() >= 3 &&
                             loan.getAmount() != null &&
                             loan.getAmount().compareTo(java.math.BigDecimal.ZERO) > 0 &&
                             loan.getInterestRate() > 0 &&
                             loan.getStartDate() != null &&
                             loan.getStatus() != null;

        if (!basicValid) return false;

        // Database uniqueness check (example: ensure no duplicate loan for same customer and date)
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT COUNT(*) FROM loan WHERE customer_name = ? AND start_date = ?"
             )) {
            ps.setString(1, loan.getCustomerName());
            ps.setDate(2, new java.sql.Date(loan.getStartDate().getTime()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(">>> DB check (Loan): existing loan count = " + count);
                return count == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
