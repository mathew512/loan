package app.action;

import app.dao.GenericDao;
import app.dao.RepaymentDao;
import app.model.Repayment;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/repayment_lists")
public class RepaymentList extends BaseActionList<Repayment> {

    @Inject
    private RepaymentDao repaymentDao;   // ✅ Inject RepaymentDao

    @Override
    public GenericDao<Repayment, Integer> getGenericDao() {
        return repaymentDao;   // ✅ return injected DAO
    }
}
