package app.utility.general;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Dependent
public class LoanApplication {

    // ================= FIELD INJECTION =================
    @Inject
    @Named("customerDS")   
    private DataSource fieldDataSource;

    // ================= CONSTRUCTOR INJECTION =================
    private final DataSource constructorDataSource;

    @Inject
    public LoanApplication(
        @Named("customerDS") DataSource constructorDataSource   
    ) {
        System.out.println(">>> Constructor injection: DataSource injected into LoanApplication");
        this.constructorDataSource = constructorDataSource;
    }

    // ================= SETTER INJECTION =================
    private DataSource setterDataSource;

    @Inject
    public void setDataSource(
        @Named("customerDS") DataSource setterDataSource   
    ) {
        System.out.println(">>> Setter injection: DataSource injected into LoanApplication");
        this.setterDataSource = setterDataSource;
    }

    // ================= BUSINESS LOGIC =================
    public boolean exists(String nationalId) {
        System.out.println(">>> Checking loan application existence for national ID: " + nationalId);

        try (Connection conn = fieldDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT COUNT(*) FROM customers WHERE national_id = ?")) {

            ps.setString(1, nationalId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}