package app.shared;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/footer")
public class FooterPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        writer.println("<div style='margin-top:40px;text-align:center;color:#888;'>");
        writer.println("<hr/>");

        writer.println("<p>");
        writer.println("<a href='./home'>Home</a> | ");
        writer.println("<a href='./aboutus'>About</a> | ");
        writer.println("<a href='./logout'>Logout</a>");
        writer.println("</p>");

        writer.println("<small>Loan System © 2026</small>");
        writer.println("</div>");
    }
}