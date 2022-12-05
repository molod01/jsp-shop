package servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.UserDAO;
import entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class ConfirmEmailServlet extends HttpServlet {
    @Inject
    UserDAO userDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userid");
        String confirm = req.getParameter("confirm");
        if (confirm != null) {
            User user = userId == null ? (User) req.getAttribute("authUser") : userDAO.getUserById(userId);
            try {
                if (userDAO.confirmEmail(user, confirm)) {
                    req.setAttribute("confirmed", "Ok");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                req.setAttribute("confirmed", ex.getMessage());
            }
        }
        req.setAttribute("pageBody", "confirm_email.jsp");
        req.getRequestDispatcher("/WEB-INF/_layout.jsp").forward(req, resp);
    }
}
