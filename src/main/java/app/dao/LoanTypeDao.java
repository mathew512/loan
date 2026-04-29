package app.dao;

import app.model.LoanType;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.sql.DataSource;

@Dependent
public class LoanTypeDao extends GenericDao<LoanType, Integer> {

    @Inject
    public LoanTypeDao(@Named("loanTypeDS") DataSource ds) {
        super(LoanType.class);
        setDs(ds);   // ✅ injects loan type datasource
    }
}
