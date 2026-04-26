package app.action;

import java.io.IOException;

import app.model.Customer;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.inject.Inject;
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

    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationChoice.CUSTOMER)
    private Validate<Customer> validate;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Customer customer = super.serializeForm(req.getParameterMap());

        if (validate.isValid(customer)) {
            super.doPost(req, resp);
        } else {
            resp.sendRedirect("./customer_lists");
        }
    }
}
