package app.action;

import app.framework.LoanFramework;
import app.framework.PageContent;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class BaseActionList<T> extends BaseAction<T> {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            List<T> data = returnData();

            if (data == null) {
                data = Collections.emptyList();
            }

            request.setAttribute(
                    PageContent.CONTENT.name(),
                    LoanFramework.htmlTable(getType(), data)
            );

            RequestDispatcher rd = request.getRequestDispatcher("./app_page");
            rd.include(request, response);

        } catch (Exception e) {
            throw new ServletException("Error rendering list page", e);
        }
    }
}