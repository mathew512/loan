package app.utility.bootstrap;

import app.utility.db.DataSourceHelper;
import app.utility.db.TableGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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
@ApplicationScoped
public class LoanDatabaseBootstrap implements Bootstrap {

    // ================= FIELD INJECTION =================
    @Inject
    @InitBootstrap
    private DataSourceHelper dsHelper;

    @Override
    public void process() {
        try {
            createDatabaseIfNotExists();

            // Use CDI-managed DataSourceHelper
            DataSource ds = dsHelper.getDataSource();

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
                dsHelper.getBaseUrlWithoutDB(),
                dsHelper.getUser(),
                dsHelper.getPassword());
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE DATABASE " + dsHelper.getDbName();
            stmt.executeUpdate(sql);

            System.out.println(">>> Database ensured.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
