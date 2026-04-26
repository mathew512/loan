package app.framework;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/app_page")
public class AppPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Loan Management System</title>");

        out.println("<style>");

        out.println(":root { --primary:#fca311; --dark:#0b1320; --card:#14213d; --light:#f8fafc; }");

        out.println("*{margin:0;padding:0;box-sizing:border-box;}");
        out.println("body{font-family:Segoe UI,Roboto,Arial,sans-serif;background:var(--dark);color:#fff;}");

        out.println(".navbar{display:flex;justify-content:space-between;align-items:center;padding:15px 30px;background:#111827;}");
        out.println(".logo{font-size:22px;font-weight:600;color:var(--primary);}");
        out.println(".nav-links{display:flex;gap:10px;}");
        out.println(".nav-links a{color:#fff;text-decoration:none;padding:10px 15px;border-radius:6px;transition:.3s;}");
        out.println(".nav-links a:hover{background:var(--primary);color:#000;}");

        out.println(".hero{padding:60px 20px;text-align:center;background:linear-gradient(135deg,#111827,#0b1320);}");
        out.println(".hero h1{font-size:38px;color:var(--primary);margin-bottom:10px;}");
        out.println(".hero p{font-size:18px;color:#d1d5db;}");

        out.println(".container{width:90%;max-width:1200px;margin:40px auto;}");

        out.println(".card{background:var(--card);padding:20px;border-radius:12px;box-shadow:0 0 15px rgba(252,163,17,0.1);}");
        out.println(".card h3{color:var(--primary);}");
        out.println(".card p{color:#ddd;}");

        out.println("table{width:100%;border-collapse:collapse;margin-top:15px;}");
        out.println("th,td{padding:12px;text-align:left;border-bottom:1px solid #333;}");
        out.println("th{background:var(--primary);color:#000;}");
        out.println("tr:hover{background:#1f2937;}");

        out.println(".form-group{margin-bottom:15px;}");
        out.println("label{display:block;margin-bottom:5px;color:#fff;}");
        out.println("input,select{width:100%;padding:10px;border-radius:8px;border:none;background:#1f2937;color:#fff;}");
        out.println("input:focus{outline:2px solid var(--primary);} ");

        out.println(".btn{background:var(--primary);color:#000;padding:10px 15px;border:none;border-radius:8px;cursor:pointer;font-weight:bold;}");
        out.println(".btn:hover{opacity:0.8;}");

        out.println(".back-link{display:inline-block;margin-top:20px;color:var(--primary);text-decoration:none;}");
        out.println(".back-link:hover{text-decoration:underline;}");

        out.println("</style>");
        out.println("</head>");

        out.println("<body>");

        /* NAVBAR */
        out.println("<div class='navbar'>");
        out.println("<div class='logo'>Loan System</div>");

        out.println("<div class='nav-links'>");
        out.println("<a href='/Loan/home'>Home</a>");

        // dynamic menu
        out.println(LoanFramework.generateMenu());

        out.println("</div>");
        out.println("</div>");

        /* PAGE CONTENT */
        out.println("<div class='container'>");

        Object content = request.getAttribute(PageContent.CONTENT.name());

        if (content == null) {
            out.println("<h3 style='color:red;'>No content loaded. Check servlet routing or BaseAction.</h3>");
        } else {
            out.println(content);
        }

        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
}