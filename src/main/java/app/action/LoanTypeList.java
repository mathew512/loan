package app.action;

import app.dao.LoanTypeDao;
import app.dao.GenericDao;
import app.model.LoanType;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/loan_type_lists")
public class LoanTypeList extends BaseActionList<LoanType> {

    @Inject
    private LoanTypeDao loanTypeDao;

    @Override
    public GenericDao<LoanType, Integer> getGenericDao() {
        return loanTypeDao;   // ✅ injected DAO
    }
}
