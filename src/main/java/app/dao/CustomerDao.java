package app.dao;

import app.model.Customer;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.sql.DataSource;

@Dependent
public class CustomerDao extends GenericDao<Customer, Integer> {

    @Inject
    public CustomerDao(@Named("customerDS") DataSource ds) {
        super(Customer.class);
        setDs(ds);   // ✅ override default datasource
    }
}
