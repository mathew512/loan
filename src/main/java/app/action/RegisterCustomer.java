package app.action;

import java.io.IOException;

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

    // Field injection
    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationChoice.CUSTOMER)
    private Validate<Customer> fieldValidator;

    @Inject
    @Named("customerDS")
    private DataSource fieldDataSource;

    // Constructor injection
    private final Validate<Customer> constructorValidator;
    private final DataSource constructorDataSource;

    @Inject
    public RegisterCustomer(
        @ValidatorQualifier(ValidatorQualifier.ValidationChoice.CUSTOMER)
        Validate<Customer> constructorValidator,

        @Named("customerDS")   //  MUST BE HERE
        DataSource ds
    ) {
        System.out.println(">>> Constructor injection: CustomerValidator + DataSource injected");
        this.constructorValidator = constructorValidator;
        this.constructorDataSource = ds;
    }

    // Setter injection
    private Validate<Customer> setterValidator;
    private DataSource setterDataSource;

    @Inject
    public void setValidatorAndDataSource(
        @ValidatorQualifier(ValidatorQualifier.ValidationChoice.CUSTOMER)
        Validate<Customer> setterValidator,

        @Named("customerDS")   // MUST BE HERE
        DataSource ds
    ) {
        System.out.println(">>> Setter injection: CustomerValidator + DataSource injected");
        this.setterValidator = setterValidator;
        this.setterDataSource = ds;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer customer = super.serializeForm(req.getParameterMap());

        if (fieldValidator.isValid(customer)) {
            System.out.println(">>> Field validator used with DataSource = " + fieldDataSource);
            super.doPost(req, resp);
        } else if (constructorValidator.isValid(customer)) {
            System.out.println(">>> Constructor validator used with DataSource = " + constructorDataSource);
            super.doPost(req, resp);
        } else if (setterValidator.isValid(customer)) {
            System.out.println(">>> Setter validator used with DataSource = " + setterDataSource);
            super.doPost(req, resp);
        } else {
            resp.sendRedirect("./customer_lists");
        }
    }
}
