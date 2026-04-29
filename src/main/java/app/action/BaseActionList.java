package app.action;

import app.dao.GenericDao;
import app.framework.PageContent;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public abstract class BaseActionList<T> extends BaseAction<T> {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<T> data = returnData();
            if (data == null) {
                data = Collections.emptyList();
            }

            // ✅ Use injected framework from BaseAction
            request.setAttribute(
                    PageContent.CONTENT.name(),
                    framework.htmlTable(getType(), data)
            );

            RequestDispatcher rd = request.getRequestDispatcher("./app_page");
            rd.include(request, response);

        } catch (Exception e) {
            throw new ServletException("Error rendering list page", e);
        }
    }

    @Override
    public List<T> returnData() {
        return getGenericDao().findAll();   // ✅ delegate to injected DAO
    }

    // Subclasses must override getGenericDao() to provide their DAO
    @Override
    public abstract GenericDao<T, Integer> getGenericDao();
}
