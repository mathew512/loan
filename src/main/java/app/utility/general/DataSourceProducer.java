package app.utility.general;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import javax.sql.DataSource;

import app.utility.bootstrap.InitBootstrap;
import app.utility.db.DataSourceHelper;

@ApplicationScoped
public class DataSourceProducer {

    @Inject
    @InitBootstrap
    private DataSourceHelper dsHelper;   // ✅ CDI-managed instance

    // Default datasource
    @Produces
    @Named("defaultDS")
    public DataSource produceDefaultDataSource() {
        return dsHelper.getDataSource();
    }

    // Named datasource for CustomerValidator
    @Produces
    @Named("customerDS")
    public DataSource produceCustomerDataSource() {
        return dsHelper.getDataSource();
    }

    // Named datasource for LoanTypeValidator
    @Produces
    @Named("loanTypeDS")
    public DataSource produceLoanTypeDataSource() {
        return dsHelper.getDataSource();
    }

    // Named datasource for RepaymentValidator
    @Produces
    @Named("repaymentDS")
    public DataSource produceRepaymentDataSource() {
        return dsHelper.getDataSource();
    }

    // Named datasource for LoanValidator
    @Produces
    @Named("loanDS")
    public DataSource produceLoanDataSource() {
        return dsHelper.getDataSource();
    }
}
