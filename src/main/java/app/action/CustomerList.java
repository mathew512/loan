package app.action;

import app.dao.CustomerDao;
import app.dao.GenericDao;
import app.model.Customer;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/customer_lists")
public class CustomerList extends BaseActionList<Customer> {

    @Inject
    private CustomerDao customerDao;

    @Override
    public GenericDao<Customer, Integer> getGenericDao() {
        return customerDao;   // ✅ injected DAO
    }
}
