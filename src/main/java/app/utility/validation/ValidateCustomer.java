package app.utility.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.model.Customer;

@Named("customerValidator")
@ValidatorQualifier(ValidatorQualifier.ValidationChoice.CUSTOMER)
@ApplicationScoped
public class ValidateCustomer implements Validate<Customer> {

    // Field injection with explicit qualifier
    @Inject
    @Named("customerDS")
    private DataSource fieldDataSource;

    // Constructor injection with explicit qualifier
    private final DataSource constructorDataSource;

    @Inject
    public ValidateCustomer(@Named("customerDS") DataSource ds) {
        System.out.println(">>> Constructor injection: DataSource injected into ValidateCustomer (customerDS)");
        this.constructorDataSource = ds;
    }

    // Setter injection with explicit qualifier
    private DataSource setterDataSource;

    @Inject
    public void setDataSource(@Named("customerDS") DataSource ds) {
        System.out.println(">>> Setter injection: DataSource injected into ValidateCustomer (customerDS)");
        this.setterDataSource = ds;
    }

    @Override
    public boolean isValid(Customer customer) {
        boolean basicValid = customer != null &&
                             customer.getFullName() != null &&
                             customer.getFullName().trim().length() >= 3 &&
                             customer.getPhone() != null &&
                             customer.getNationalId() != null;

        if (!basicValid) return false;

        try (Connection conn = fieldDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM customers WHERE national_id = ?")) {
            ps.setString(1, customer.getNationalId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println(">>> DB check (Customer): national_id count = " + count);
                return count == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
