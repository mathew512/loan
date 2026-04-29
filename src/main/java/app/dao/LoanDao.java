package app.dao;

import app.model.Loan;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.sql.DataSource;

@Dependent
public class LoanDao extends GenericDao<Loan, Integer> {

    @Inject
    public LoanDao(@Named("loanDS") DataSource ds) {
        super(Loan.class);
        setDs(ds);   // ✅ injects loan datasource
    }
}
