package app.action;

import java.io.IOException;
import app.framework.PageContent;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/home")
public class HomePage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ Session validation (Loan-specific)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("UserActualName") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        StringBuilder html = new StringBuilder();

        /* Hero */
        html.append("<div class='hero'>");
        html.append("<h1>Fast & Secure Loan Management System</h1>");
        html.append("<p>Apply, track, and manage loans efficiently with transparency and speed.</p>");
        html.append("</div>");

        /* Features */
        html.append("<div class='section'>");
        html.append("<h2>Core Features</h2>");
        html.append("<div class='features'>");

        html.append("<div class='card'>");
        html.append("<h3>Loan Applications</h3>");
        html.append("<p>Submit and manage loan requests in real time.</p>");
        html.append("</div>");

        html.append("<div class='card'>");
        html.append("<h3>Approval System</h3>");
        html.append("<p>Automated loan approval workflow with validation rules.</p>");
        html.append("</div>");

        html.append("<div class='card'>");
        html.append("<h3>Repayment Tracking</h3>");
        html.append("<p>Monitor repayments and outstanding balances easily.</p>");
        html.append("</div>");

        html.append("<div class='card'>");
        html.append("<h3>Analytics Dashboard</h3>");
        html.append("<p>View loan performance and financial insights.</p>");
        html.append("</div>");

        html.append("</div>");
        html.append("</div>");

        // ✅ Pass content to AppPage
        request.setAttribute(PageContent.CONTENT.name(), html.toString());
        RequestDispatcher rd = request.getRequestDispatcher("./app_page");
        rd.include(request, response); // use include for consistency with Cohort12
    }
}
