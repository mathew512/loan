package app.action;

import app.model.LoanType;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/loan_type_lists")
public class LoanTypeList extends BaseActionList<LoanType> {
}