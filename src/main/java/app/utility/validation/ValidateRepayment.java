package app.utility.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.model.Repayment;

@Named("repaymentValidator")
@ValidatorQualifier(ValidatorQualifier.ValidationChoice.REPAYMENT)
@ApplicationScoped
public class ValidateRepayment implements Validate<Repayment> {

    // Field injection: CDI sets DataSource directly into this field (@Default qualifier)
    @Inject
    @Named("repaymentDS")
    private DataSource fieldDataSource;

    // Constructor injection: CDI calls this constructor and passes DataSource (@Named qualifier)
    private final DataSource constructorDataSource;

    @Inject
    public ValidateRepayment(@Named("repaymentDS") DataSource ds) {
        System.out.println(">>> Constructor injection: DataSource injected into ValidateRepayment (Named)");
        this.constructorDataSource = ds;
    }

    // Setter injection: CDI calls this method after construction and sets DataSource (@Named qualifier)
    private DataSource setterDataSource;

    @Inject
    public void setDataSource(@Named("repaymentDS") DataSource ds) {
        System.out.println(">>> Setter injection: DataSource injected into ValidateRepayment (Named)");
        this.setterDataSource = ds;
    }

    // Business logic: validate Repayment fields and check DB for loan existence
    @Override
    public boolean isValid(Repayment repayment) {
        boolean basicValid = repayment != null &&
                             repayment.getAmountPaid() != null &&
                             repayment.getAmountPaid().compareTo(BigDecimal.ZERO) > 0 &&
                             repayment.getPaymentDate() != null;

        if (!basicValid) return false;

        try (Connection conn = fieldDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM loan WHERE id = ?")) {
            ps.setInt(1, repayment.getLoanId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(">>> DB check (Repayment): loanId exists count = " + count);
                return count > 0; // valid only if loan exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
