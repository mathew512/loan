package app.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        // ================= GLOBAL APP NAME =================
        context.setAttribute("applicationName", "LOAN SYSTEM | SMART FINANCE");

        System.out.println("====================================");
        System.out.println(" Loan System Starting Up...");
        System.out.println(" Framework initialized successfully");
        System.out.println(" Application Name set in context");
        System.out.println("====================================");

        // TODO LATER:
        // - Initialize PostgreSQL connection pool
        // - Auto-create tables if needed
        // - Load system configs
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Loan System shutting down...");
    }
}