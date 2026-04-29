package app.dao;

import app.model.Repayment;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.sql.DataSource;

@Dependent
public class RepaymentDao extends GenericDao<Repayment, Integer> {

    @Inject
    public RepaymentDao(@Named("repaymentDS") DataSource ds) {
        super(Repayment.class);
        setDs(ds);   // ✅ injects repayment datasource
    }
}
