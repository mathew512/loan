package app.action;

import java.io.IOException;

import app.dao.GenericDao;
import app.dao.RepaymentDao;
import app.model.Repayment;
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
        urlPatterns = { "/register_repayment" },
        initParams = {
                @WebInitParam(name = "pageName", value = "Repayment Form"),
                @WebInitParam(name = "pageHeader", value = "Loan Repayment Module")
        }
)
public class RegisterRepayment extends BaseAction<Repayment> {

    // Field injection
    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationChoice.REPAYMENT)
    private Validate<Repayment> fieldValidator;

    @Inject
    @Named("repaymentDS")
    private DataSource fieldDataSource;

    // Constructor injection
    private final Validate<Repayment> constructorValidator;
    private final DataSource constructorDataSource;

    @Inject
    public RegisterRepayment(
        @ValidatorQualifier(ValidatorQualifier.ValidationChoice.REPAYMENT) Validate<Repayment> constructorValidator,
        @Named("repaymentDS") DataSource ds
    ) {
        System.out.println(">>> Constructor injection: RepaymentValidator + DataSource injected (repaymentDS)");
        this.constructorValidator = constructorValidator;
        this.constructorDataSource = ds;
    }

    // Setter injection
    private Validate<Repayment> setterValidator;
    private DataSource setterDataSource;

    @Inject
    public void setValidatorAndDataSource(
        @ValidatorQualifier(ValidatorQualifier.ValidationChoice.REPAYMENT) Validate<Repayment> setterValidator,
        @Named("repaymentDS") DataSource ds
    ) {
        System.out.println(">>> Setter injection: RepaymentValidator + DataSource injected (repaymentDS)");
        this.setterValidator = setterValidator;
        this.setterDataSource = ds;
    }

    // ✅ Inject RepaymentDao
    @Inject
    private RepaymentDao repaymentDao;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Repayment repayment = super.serializeForm(req.getParameterMap());

        if (fieldValidator.isValid(repayment)) {
            System.out.println(">>> Field validator used with DataSource = " + fieldDataSource);
            super.doPost(req, resp);
        } else if (constructorValidator.isValid(repayment)) {
            System.out.println(">>> Constructor validator used with DataSource = " + constructorDataSource);
            super.doPost(req, resp);
        } else if (setterValidator.isValid(repayment)) {
            System.out.println(">>> Setter validator used with DataSource = " + setterDataSource);
            super.doPost(req, resp);
        } else {
            resp.sendRedirect("./repayment_lists");
        }
    }

    @Override
    public GenericDao<Repayment, Integer> getGenericDao() {
        return repaymentDao;   // ✅ return injected DAO
    }
}
