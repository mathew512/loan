package app.shared;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/", "/aboutus"})
public class AboutUsPage extends GenericServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter writer = res.getWriter();

        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Loan System - About Us</title>");

        writer.println("<style>");
        writer.println("body { font-family: Arial; margin: 0; background-color: #f4f6f8; }");
        writer.println("header { background-color: #0f172a; color: white; padding: 20px; text-align: center; }");
        writer.println("nav { background:#1e293b; padding: 12px; text-align:center; }");
        writer.println("nav a { color:white; margin:0 15px; text-decoration:none; font-weight:bold; }");
        writer.println("nav a:hover { color:#fca311; }");
        writer.println("section { margin: 20px; padding: 20px; background: white; border-radius: 8px; }");
        writer.println("footer { margin-top: 30px; padding: 20px; background:#0f172a; color:white; text-align:center; }");
        writer.println("footer a { color:#fca311; text-decoration:none; }");
        writer.println("</style>");

        writer.println("</head>");
        writer.println("<body>");

        // ================= HEADER =================
        writer.println("<header>");
        writer.println("<h1>Loan Management System</h1>");
        writer.println("<p>Welcome to our Financial Management Platform</p>");
        writer.println("</header>");

        // ================= NAVBAR =================
        writer.println("<nav>");
        writer.println("<a href='./'>About Us</a>");
        writer.println("<a href='./login'>Login</a>");
        writer.println("</nav>");

        // ================= CONTENT =================
        writer.println("<section>");
        writer.println("<h2>Who We Are</h2>");
        writer.println("<p>We are a modern loan management system built using Jakarta EE, designed to simplify financial operations.</p>");
        writer.println("</section>");

        writer.println("<section>");
        writer.println("<h2>Our Mission</h2>");
        writer.println("<p>To provide a reliable, scalable, and easy-to-use loan management platform for businesses and customers.</p>");
        writer.println("</section>");

        writer.println("<section>");
        writer.println("<h2>What We Offer</h2>");
        writer.println("<ul>");
        writer.println("<li>Customer Management</li>");
        writer.println("<li>Loan Processing</li>");
        writer.println("<li>Repayment Tracking</li>");
        writer.println("<li>Loan Type Configuration</li>");
        writer.println("</ul>");
        writer.println("</section>");

        writer.println("<section>");
        writer.println("<h2>Why Choose Us</h2>");
        writer.println("<p>We use a clean architecture, fast processing, and a scalable framework built for real-world financial systems.</p>");
        writer.println("</section>");

        // ================= FOOTER =================
        writer.println("<footer>");
        writer.println("<p>&copy; 2026 Loan System | All Rights Reserved</p>");
        writer.println("<p><a href='./contactus.jsp'>Contact Us</a></p>");
        writer.println("</footer>");

        writer.println("</body>");
        writer.println("</html>");
    }
}
