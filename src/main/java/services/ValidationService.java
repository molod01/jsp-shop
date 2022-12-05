package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.UserDAO;
import entities.User;

import javax.servlet.http.Part;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class ValidationService {
    @Inject
    private UserDAO userDAO;

    public boolean isValidInput(String input) {
        return input != null && !input.isBlank();
    }

    public boolean isValidLogin(String login, boolean checkIsLoginFree) {
        try {
            isValidInput(login);
            if (!login.equals(login.trim())) {
                throw new Exception("Login could not have trailing spaces");
            }
            if (checkIsLoginFree) {
                if (!userDAO.isLoginFree(login)) {
                    throw new Exception("Login already in use");
                }
            }
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean isValidPasswordUpdate(String oldPass, String newPass, String confirmNewPass, User authUser) {
        try {
            if (isValidInput(oldPass) && isValidInput(confirmNewPass)) {
                if (!authUser.getPass().equals(userDAO.makePasswordHash(oldPass, authUser.getSalt()))) {
                    throw new Exception("Wrong old password");
                }
                if (!isValidInput(newPass)) {
                    throw new Exception("Password could not be empty");
                }
                if (!newPass.equals(confirmNewPass)) {
                    throw new Exception("Password mismatch");
                }
                return true;
            } else return false;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean isValidAvatarUpdate(Part avatar) {
        return avatar != null && avatar.getSize() > 0;
    }

    public boolean isValidEmail(String email) {
        if (!isValidInput(email)) return false;
        Pattern regex = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
        Matcher matcher = regex.matcher(email);
        return matcher.find();
    }

    public boolean isValidPassword(String password, String confirmPassword) {
        try {
            if (password == null || password.isEmpty()) {
                throw new Exception("Password could not be empty");
            }
            if (!password.equals(confirmPassword)) {
                throw new Exception("Password mismatch");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean isValidUsername(String username) {
        try {
            if (username == null || username.isEmpty()) {
                throw new Exception("Name could not be empty");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean isChanged(String oldField, String newField) {
        if (oldField == null) {
            return newField != null && !newField.isBlank();
        } else if (newField != null) return !oldField.equals(newField);
        return false;
    }
}
