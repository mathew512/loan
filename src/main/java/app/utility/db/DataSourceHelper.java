package app.utility.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

import app.utility.db.TableGenerator;
import app.model.*;

public class DataSourceHelper {

    private static volatile HikariDataSource dataSource;

    private static final String HOST = "localhost";
    private static final int PORT = 5432;
    private static final String DB_NAME = "loan_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "newpassword";

    private DataSourceHelper() {}

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (DataSourceHelper.class) {
                if (dataSource == null) {
                    dataSource = createDataSource();
                    initTables(dataSource);
                }
            }
        }
        return dataSource;
    }

    // ================= CREATE POOL =================
    private static HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB_NAME);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setDriverClassName("org.postgresql.Driver");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);

        // recommended for stability
        config.setConnectionTimeout(30000);
        config.setPoolName("LoanAppPool");

        return new HikariDataSource(config);
    }

    // ================= TABLE INIT =================
    private static void initTables(DataSource ds) {
        Set<Class<?>> entities = new HashSet<>();

        // NOTE: you can later auto-scan instead of manual listing
        entities.add(Customer.class);
        entities.add(Loan.class);
        entities.add(LoanType.class);
        entities.add(Repayment.class);

        TableGenerator.generateTables(ds, entities);
    }

    // ================= CLEAN SHUTDOWN =================
    public static void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    // ================= INFO =================
    public static String getDbName() { return DB_NAME; }
    public static String getUser() { return USER; }
}