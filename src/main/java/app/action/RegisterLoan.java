package app.action;

import app.model.Loan;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(
        name = "Register Loan",
        urlPatterns = { "/register_loan" },
        initParams = {
                @WebInitParam(name = "pageName", value = "Loan Form"),
                @WebInitParam(name = "pageHeader", value = "Loan Registration")
        }
)
public class RegisterLoan extends BaseAction<Loan> {
}