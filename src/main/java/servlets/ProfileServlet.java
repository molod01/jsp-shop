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

@WebServlet("/profile")
@MultipartConfig
@Singleton
public class ProfileServlet extends HttpServlet {
    @Inject
    ConfirmationService confirmationService;
    @Inject
    private UserDAO userDAO;
    @Inject
    private MimeService mimeService;
    @Inject
    private ValidationService validationService;
    @Inject
    private ImagesService imagesService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String updateError = (String) session.getAttribute("updateError");
        String updateOk = (String) session.getAttribute("updateOk");
        if (updateError != null) {
            req.setAttribute("updateError", updateError);
            session.removeAttribute("updateError");
        } else if (updateOk != null) {
            req.setAttribute("updateOk", updateOk);
            session.removeAttribute("updateOk");
        }
        User authUser = (User) req.getAttribute("authUser");
        if (authUser == null) {
            req.setAttribute("pageBody", "please_login.jsp");
        } else {
            req.setAttribute("pageBody", "profile.jsp");
        }
        req.getRequestDispatcher("/WEB-INF/_layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userLogin = req.getParameter("login");
        String userName = req.getParameter("name");
        String userEmail = req.getParameter("email");

        String userOldPassword = req.getParameter("oldPass");
        String userNewPassword = req.getParameter("newPass");
        String userConfirmNewPassword = req.getParameter("confirmNewPass");

        User authUser = (User) req.getAttribute("authUser");
        System.out.println(authUser);
        System.out.println(userLogin);
        System.out.println(userName);
        System.out.println(userEmail);
        System.out.println(userOldPassword);
        System.out.println(userNewPassword);
        System.out.println(userConfirmNewPassword);
        boolean isProfileChanged = false;
        try {
            //region validation
            if (validationService.isValidInput(userLogin) && !userLogin.equals(authUser.getLogin())) {
                isProfileChanged = true;
                authUser.setLogin(userLogin);
            }
            if (validationService.isValidUsername(userName) && !userName.equals(authUser.getName())) {
                isProfileChanged = true;
                authUser.setName(userName);
            }
            if (validationService.isValidEmail(userEmail) && !userEmail.equals(authUser.getEmail() == null ? "" : authUser.getEmail())) {
                isProfileChanged = true;
                authUser.setEmail(userEmail);
                confirmationService.confirmUserMail(authUser);
            }
            if (validationService.isValidPasswordUpdate(userOldPassword, userNewPassword, userConfirmNewPassword, authUser)) {
                isProfileChanged = true;
                authUser.setPass(userNewPassword);
            }
            Part userAvatar = req.getPart("avatar");
            if (validationService.isValidAvatarUpdate(userAvatar)) {
                String path = req.getServletContext().getRealPath("/");
                String avatarName = imagesService.saveImage(path + "/../Uploads/", userAvatar);
                isProfileChanged = true;
                authUser.setAvatar(avatarName);
            }
            //endregion
            if (isProfileChanged) {
                if (userDAO.update(authUser) == null) {
                    throw new Exception("Server error, try later");
                }
                session.setAttribute("updateOk", "Credentials Update Successful");
            }
        } catch (Exception ex) {
            session.setAttribute("updateError", ex.getMessage());
        }
        resp.sendRedirect(req.getRequestURI());
    }
}
