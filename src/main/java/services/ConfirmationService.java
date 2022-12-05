package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.UserDAO;
import entities.User;
import services.email.EmailService;

import java.util.UUID;

@Singleton
public class ConfirmationService {
    @Inject
    EmailService emailService;
    @Inject
    UserDAO userDAO;

    public void confirmUserMail(User user) {
        String confirmationCode = UUID.randomUUID().toString().substring(0, 6);
        user.setEmailCode(confirmationCode);
        userDAO.update(user);
        emailService.send(user.getEmail(), "Email confirmation code",
                String.format("<h2>Hello</h2><p>To confirm your E-mail type a code <b>%s</b></p>" +
                                "<p>Or follow this <a href='https://localhost:8443/WebBasics/confirm/?userid=%s&confirm=%s'>link</a>",
                        user.getEmailCode(),
                        user.getId(),
                        user.getEmailCode()));
    }
}

