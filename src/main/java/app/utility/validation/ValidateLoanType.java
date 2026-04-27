package app.utility.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.model.LoanType;

@Named("loanTypeValidator")
@ValidatorQualifier(ValidatorQualifier.ValidationChoice.LOAN_TYPE)
@ApplicationScoped
public class ValidateLoanType implements Validate<LoanType> {

    // Field injection: CDI sets DataSource directly into this field
    @Inject
    @Named("loanTypeDS")
    private DataSource fieldDataSource;

    // Constructor injection: CDI calls this constructor and passes DataSource
    private final DataSource constructorDataSource;

    @Inject
    public ValidateLoanType(@Named("loanTypeDS") DataSource ds) {
        System.out.println(">>> Constructor injection: DataSource injected into ValidateLoanType (Named)");
        this.constructorDataSource = ds;
    }

    // Setter injection: CDI calls this method after construction and sets DataSource
    private DataSource setterDataSource;

    @Inject
    public void setDataSource(@Named("loanTypeDS") DataSource ds) {
        System.out.println(">>> Setter injection: DataSource injected into ValidateLoanType (Named)");
        this.setterDataSource = ds;
    }

    // Business logic: validate LoanType fields and check DB for duplicates
    @Override
    public boolean isValid(LoanType loanType) {
        boolean basicValid = loanType != null &&
                             loanType.getName() != null &&
                             loanType.getName().trim().length() >= 3;

        if (!basicValid) return false;

        try (Connection conn = fieldDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM loan_type WHERE name = ?")) {
            ps.setString(1, loanType.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(">>> DB check (LoanType): name count = " + count);
                return count == 0; // valid only if unique
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
