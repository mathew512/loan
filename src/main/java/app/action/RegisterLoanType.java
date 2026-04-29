package app.action;

import java.io.IOException;

import app.dao.GenericDao;
import app.dao.LoanTypeDao;
import app.model.LoanType;
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
        urlPatterns = { "/register_loan_type" },
        initParams = {
                @WebInitParam(name = "pageName", value = "Loan Types"),
                @WebInitParam(name = "pageHeader", value = "Loan Configuration")
        }
)
public class RegisterLoanType extends BaseAction<LoanType> {

    // Field injection
    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationChoice.LOAN_TYPE)
    private Validate<LoanType> fieldValidator;

    @Inject
    @Named("loanTypeDS")
    private DataSource fieldDataSource;

    // Constructor injection
    private final Validate<LoanType> constructorValidator;
    private final DataSource constructorDataSource;

    @Inject
    public RegisterLoanType(
        @ValidatorQualifier(ValidatorQualifier.ValidationChoice.LOAN_TYPE) Validate<LoanType> constructorValidator,
        @Named("loanTypeDS") DataSource ds
    ) {
        System.out.println(">>> Constructor injection: LoanTypeValidator + DataSource injected");
        this.constructorValidator = constructorValidator;
        this.constructorDataSource = ds;
    }

    // Setter injection
    private Validate<LoanType> setterValidator;
    private DataSource setterDataSource;

    @Inject
    public void setValidatorAndDataSource(
        @ValidatorQualifier(ValidatorQualifier.ValidationChoice.LOAN_TYPE) Validate<LoanType> setterValidator,
        @Named("loanTypeDS") DataSource ds
    ) {
        System.out.println(">>> Setter injection: LoanTypeValidator + DataSource injected");
        this.setterValidator = setterValidator;
        this.setterDataSource = ds;
    }

    // ✅ Inject LoanTypeDao
    @Inject
    private LoanTypeDao loanTypeDao;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoanType loanType = super.serializeForm(req.getParameterMap());

        if (fieldValidator.isValid(loanType)) {
            System.out.println(">>> Field validator used with DataSource = " + fieldDataSource);
            super.doPost(req, resp);
        } else if (constructorValidator.isValid(loanType)) {
            System.out.println(">>> Constructor validator used with DataSource = " + constructorDataSource);
            super.doPost(req, resp);
        } else if (setterValidator.isValid(loanType)) {
            System.out.println(">>> Setter validator used with DataSource = " + setterDataSource);
            super.doPost(req, resp);
        } else {
            resp.sendRedirect("./loan_type_lists");
        }
    }

    @Override
    public GenericDao<LoanType, Integer> getGenericDao() {
        return loanTypeDao;   // ✅ return injected DAO
    }
}
