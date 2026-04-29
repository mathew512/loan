package app.action;

import java.io.IOException;

import app.dao.GenericDao;
import app.dao.CustomerDao;
import app.model.Customer;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import javax.sql.DataSource;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(
        urlPatterns = { "/register_customer" },
        initParams = {
                @WebInitParam(name = "pageName", value = "Register Customer"),
                @WebInitParam(name = "pageHeader", value = "Loan System - Customer Registration")
        }
)
public class RegisterCustomer extends BaseAction<Customer> {

    private final Validate<Customer> validator;
    private final DataSource dataSource;

    @Inject
    private CustomerDao customerDao;   // ✅ Inject CustomerDao

    @Inject
    public RegisterCustomer(
        @ValidatorQualifier(ValidatorQualifier.ValidationChoice.CUSTOMER) Validate<Customer> validator,
        @Named("customerDS") DataSource ds
    ) {
        this.validator = validator;
        this.dataSource = ds;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer customer = super.serializeForm(req.getParameterMap());

        if (validator.isValid(customer)) {
            System.out.println(">>> Constructor validator used with DataSource = " + dataSource);
            super.doPost(req, resp);
        } else {
            resp.sendRedirect("./customer_lists");
        }
    }

    @Override
    public GenericDao<Customer, Integer> getGenericDao() {
        return customerDao;   // ✅ return injected DAO
    }
}
