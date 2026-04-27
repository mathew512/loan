package app.utility.general;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import javax.sql.DataSource;

import app.utility.db.DataSourceHelper;

@ApplicationScoped
public class DataSourceProducer {

    // Named datasource for CustomerValidator
    @Produces
    @Named("customerDS")
    public DataSource produceCustomerDataSource() {
        return DataSourceHelper.getDataSource();
    }

    // Named datasource for LoanTypeValidator
    @Produces
    @Named("loanTypeDS")
    public DataSource produceLoanTypeDataSource() {
        return DataSourceHelper.getDataSource();
    }

    // Named datasource for RepaymentValidator
    @Produces
    @Named("repaymentDS")
    public DataSource produceRepaymentDataSource() {
        return DataSourceHelper.getDataSource();
    }
}
