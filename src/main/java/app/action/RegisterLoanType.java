package app.action;

import java.io.IOException;

import app.model.LoanType;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.inject.Inject;
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

    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationChoice.LOAN_TYPE)
    private Validate<LoanType> validate;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoanType loanType = super.serializeForm(req.getParameterMap());

        if (validate.isValid(loanType)) {
            super.doPost(req, resp);
        } else {
            resp.sendRedirect("./loan_type_lists");
        }
    }
}
