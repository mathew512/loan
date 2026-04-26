package app.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(false);

        String contextPath = httpRequest.getContextPath();
        String defaultUri = contextPath + "/";
        String loginUri = contextPath + "/login";
        String indexUri = contextPath + "/index.jsp";

        // allow contact pages without login
        String contactUsUri = "contactus.jsp";
        String contactDisplayUri = "contact_us_display.jsp";

        String pageRequestUri = httpRequest.getRequestURI();
        boolean loggedIn = session != null && session.getAttribute("UserActualName") != null;

        if (loggedIn
                || pageRequestUri.equalsIgnoreCase(loginUri)
                || pageRequestUri.equalsIgnoreCase(defaultUri)
                || pageRequestUri.equalsIgnoreCase(indexUri)
                || pageRequestUri.contains("aboutus")
                || pageRequestUri.contains(contactUsUri)
                || pageRequestUri.contains(contactDisplayUri)) {

            filterChain.doFilter(servletRequest, servletResponse);

        } else {
            if (session != null) {
                session.invalidate();
            }
            httpResponse.sendRedirect(loginUri);
        }
    }
}
