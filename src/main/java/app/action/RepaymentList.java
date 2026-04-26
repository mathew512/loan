package app.action;

import app.model.Repayment;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/repayment_lists")
public class RepaymentList extends BaseActionList<Repayment> {
}