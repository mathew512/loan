package app.action;

import java.io.IOException;

import app.model.Repayment;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.inject.Inject;
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

    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationChoice.REPAYMENT)
    private Validate<Repayment> validate;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Repayment repayment = super.serializeForm(req.getParameterMap());

        if (validate.isValid(repayment)) {
            super.doPost(req, resp);
        } else {
            resp.sendRedirect("./repayment_lists");
        }
    }
}
