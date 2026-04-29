package app.action;

import app.dao.GenericDao;
import app.dao.LoanDao;
import app.model.Loan;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(
        name = "Register Loan",
        urlPatterns = { "/register_loan" },
        initParams = {
                @WebInitParam(name = "pageName", value = "Loan Form"),
                @WebInitParam(name = "pageHeader", value = "Loan Registration")
        }
)
public class RegisterLoan extends BaseAction<Loan> {

    private final Validate<Loan> validate;

    @Inject
    private LoanDao loanDao;   // ✅ Inject LoanDao

    @Inject
    public RegisterLoan(@ValidatorQualifier(ValidatorQualifier.ValidationChoice.LOAN) Validate<Loan> validate) {
        this.validate = validate;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Loan loan = super.serializeForm(req.getParameterMap());

        if (validate.isValid(loan)) {
            super.doPost(req, resp);   // persist loan if valid
        } else {
            resp.sendRedirect("./loan_lists");   // redirect if invalid
        }
    }

    @Override
    public GenericDao<Loan, Integer> getGenericDao() {
        return loanDao;   // ✅ return injected DAO
    }
}
