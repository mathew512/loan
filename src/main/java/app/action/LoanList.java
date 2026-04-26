package app.action;

import app.model.Loan;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/loan_lists")
public class LoanList extends BaseActionList<Loan> {
}