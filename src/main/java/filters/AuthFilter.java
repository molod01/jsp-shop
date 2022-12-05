package filters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.UserDAO;
import entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Singleton
public class AuthFilter implements Filter {
    private final UserDAO userDAO;
    private FilterConfig filterConfig;

    @Inject
    public AuthFilter(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        if (request.getParameter("logout") != null) {
            session.removeAttribute("authUserId");
            response.sendRedirect("/");
            return;
        }
        if (request.getMethod().equalsIgnoreCase("POST")) {
            if ("nav-auth-form".equals(request.getParameter("form-id"))) {
                String userLogin = request.getParameter("userLogin");
                String userPassword = request.getParameter("userPassword");
                User user = userDAO.getUserByCredentials(userLogin, userPassword);
                if (user == null) {
                    session.setAttribute("authError", "Credentials incorrect");
                } else {
                    session.setAttribute("authUserId", user.getId());
                }
                if(request.getQueryString() != null){
                    response.sendRedirect(request.getContextPath() + "?" + request.getQueryString());
                }
                else response.sendRedirect(request.getContextPath());
                return;
            }
        }

        String authError = (String) session.getAttribute("authError");
        String userId = (String) session.getAttribute("authUserId");

        if (authError != null) {
            request.setAttribute("authError", authError);
            session.removeAttribute("authError");
        }
        if (userId != null) {
            request.setAttribute("authUser", userDAO.getUserById(userId));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
        this.filterConfig = null;
    }
}
