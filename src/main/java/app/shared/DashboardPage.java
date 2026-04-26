package app.shared;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/dashboard")
public class DashboardPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Loan System - Dashboard</title>");
        out.println("<script src='https://cdn.jsdelivr.net/npm/chart.js'></script>");
        out.println("<style>");
        out.println("body { font-family: Arial; margin: 0; background-color: #f4f6f8; }");
        out.println("header { background-color: #0f172a; color: white; padding: 20px; text-align: center; }");
        out.println("nav { background:#1e293b; padding: 12px; text-align:center; }");
        out.println("nav a { color:white; margin:0 15px; text-decoration:none; font-weight:bold; }");
        out.println("nav a:hover { color:#fca311; }");
        out.println("section { margin: 20px; padding: 20px; background: white; border-radius: 8px; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        // ================= HEADER =================
        out.println("<header>");
        out.println("<h1>Loan Management System</h1>");
        out.println("<p>Dashboard Overview</p>");
        out.println("</header>");

        // ================= NAVBAR =================
        out.println("<nav>");
        out.println("<a href='./aboutus'>About Us</a>");
        out.println("<a href='./dashboard'>Dashboard</a>");
        out.println("<a href='./logout'>Logout</a>");
        out.println("</nav>");

        // ================= DASHBOARD CONTENT =================
        out.println("<section>");
        out.println("<h2 style='text-align:center;'>Loan Dashboard</h2>");
        out.println("<div style='width:70%;margin:auto;background:white;padding:20px;border-radius:10px;'>");
        out.println("<canvas id='loanChart'></canvas>");
        out.println("</div>");
        out.println("</section>");

        // ================= CHART SCRIPT =================
        out.println("<script>");
        out.println("const ctx = document.getElementById('loanChart');");
        out.println("new Chart(ctx, {");
        out.println("type: 'bar',");
        out.println("data: {");
        out.println("labels: ['Jan','Feb','Mar','Apr','May'],");
        out.println("datasets: [{");
        out.println("label: 'Loan Amounts',");
        out.println("data: [5000, 8000, 3000, 10000, 7000],");
        out.println("backgroundColor: '#2563eb'");
        out.println("}]");
        out.println("},");
        out.println("options: { responsive: true, plugins: { legend: { position: 'top' } } }");
        out.println("});");
        out.println("</script>");

        // ================= FOOTER =================
        out.println("<footer style='margin-top:30px;padding:20px;background:#0f172a;color:white;text-align:center;'>");
        out.println("<p>&copy; 2026 Loan System | Dashboard</p>");
        out.println("</footer>");

        out.println("</body>");
        out.println("</html>");
    }
}
