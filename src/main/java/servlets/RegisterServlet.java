package servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.UserDAO;
import entities.User;
import services.ConfirmationService;
import services.image.ImagesService;
import services.image.MimeService;
import services.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
@MultipartConfig
@Singleton
public class RegisterServlet extends HttpServlet {
    @Inject
    private UserDAO userDAO;
    @Inject
    private MimeService mimeService;
    @Inject
    private ConfirmationService confirmationService;
    @Inject
    private ValidationService validationService;

    @Inject private ImagesService imagesService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String regError = (String) session.getAttribute("regError");
        String regOk = (String) session.getAttribute("regOk");
        String[] userInput = (String[]) session.getAttribute("userInput");
        if (regError != null) {
            req.setAttribute("regError", regError);
            session.removeAttribute("regError");
        } else if (regOk != null) {
            req.setAttribute("regOk", regOk);
            session.removeAttribute("regOk");
        }
        if (userInput != null) {
            req.setAttribute("login", userInput[0]);
            req.setAttribute("username", userInput[1]);
            req.setAttribute("email", userInput[2]);
            session.removeAttribute("userInput");
        }
        req.setAttribute("pageBody", "register.jsp");
        req.getRequestDispatcher("/WEB-INF/_layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String username = req.getParameter("name");
        String avatarName = null;
        try {
            validationService.isValidLogin(login, true);
            validationService.isValidEmail(email);
            validationService.isValidPassword(password, confirmPassword);
            validationService.isValidUsername(username);
            //region upload
            Part userAvatar = req.getPart("avatar");
            if (validationService.isValidAvatarUpdate(userAvatar)) {
                String path = req.getServletContext().getRealPath("/");
                avatarName = imagesService.saveImage(path + "/../Uploads/", userAvatar);
            }
            //endregion
            User user = new User()
                    .setLogin(login)
                    .setPass(password)
                    .setName(username)
                    .setEmail(email);
            if (avatarName != null) user.setAvatar(avatarName);
            var id = userDAO.add(user);
            if (id == null) {
                throw new Exception("Server error, try later");
            } else {
                session.setAttribute("authUserId", id);
                confirmationService.confirmUserMail(user);
            }
        } catch (Exception ex) {
            session.setAttribute("regError", ex.getMessage());
            session.setAttribute("userInput", new String[]{login, username, email});
        }
        session.setAttribute("regOk", "Registration Successful!");
        resp.sendRedirect(req.getRequestURI());
    }

}
