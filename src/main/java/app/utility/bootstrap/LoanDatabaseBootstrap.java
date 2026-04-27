package app.utility.bootstrap;

import app.utility.db.DataSourceHelper;
import app.utility.db.TableGenerator;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import app.model.Customer;
import app.model.Loan;
import app.model.LoanType;
import app.model.Repayment;

@InitBootstrap
@Dependent
public class LoanDatabaseBootstrap implements Bootstrap {

    // ================= FIELD INJECTION =================
    @Inject
    @Named("loanTypeDS")   // ✅ FIXED (can be any, but consistent choice)
    private DataSource fieldDataSource;

    // ================= CONSTRUCTOR INJECTION =================
    private final DataSource constructorDataSource;

    @Inject
    public LoanDatabaseBootstrap(
        @Named("loanTypeDS") DataSource constructorDataSource   // ✅ FIXED
    ) {
        System.out.println(">>> Constructor injection: DataSource injected into LoanDatabaseBootstrap");
        this.constructorDataSource = constructorDataSource;
    }

    // ================= SETTER INJECTION =================
    private DataSource setterDataSource;

    @Inject
    public void setDataSource(
        @Named("loanTypeDS") DataSource setterDataSource   // ✅ FIXED
    ) {
        System.out.println(">>> Setter injection: DataSource injected into LoanDatabaseBootstrap");
        this.setterDataSource = setterDataSource;
    }

    @Override
    public void process() {
        try {
            createDatabaseIfNotExists();

            // Use field injection (demonstration)
            DataSource ds = fieldDataSource != null
                    ? fieldDataSource
                    : DataSourceHelper.getDataSource();

            Set<Class<?>> entities = new HashSet<>();
            entities.add(Customer.class);
            entities.add(Loan.class);
            entities.add(LoanType.class);
            entities.add(Repayment.class);

            TableGenerator.generateTables(ds, entities);

            System.out.println(">>> Loan database bootstrap completed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDatabaseIfNotExists() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://" + "localhost" + ":" + 5432 + "/",
                DataSourceHelper.getUser(),
                DataSourceHelper.getPassword());
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE DATABASE " + DataSourceHelper.getDbName();
            stmt.executeUpdate(sql);

            System.out.println(">>> Database ensured.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}