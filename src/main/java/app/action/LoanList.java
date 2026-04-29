package app.action;

import app.dao.GenericDao;
import app.dao.LoanDao;
import app.model.Loan;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/loan_lists")
public class LoanList extends BaseActionList<Loan> {   // ✅ fixed name

    @Inject
    private LoanDao loanDao;

    @Override
    public GenericDao<Loan, Integer> getGenericDao() {
        return loanDao;
    }
}
