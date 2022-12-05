package dao;

import entities.User;
import services.ValidationService;
import services.data.DataService;
import services.hash.HashService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.sql.*;
import java.util.UUID;

@Singleton
public class UserDAO {
    private final Connection connection;
    private final HashService hashService;
    private final DataService dataService;
    @Inject
    private ValidationService validationService;

    @Inject
    public UserDAO(DataService dataService, @Named("Sha1HashService") HashService hashService) {
        this.dataService = dataService;
        this.hashService = hashService;
        this.connection = dataService.getConnection();
    }
    /**
     * Inserts User to database
     *
     * @param user data to insert
     * @return user ID in table
     */
    public String add(User user) {
        // генеруемо id
        user.setId(UUID.randomUUID().toString());
        // генеруємо сіль
        user.setSalt(hashService.hash(UUID.randomUUID().toString()));
        // формуємо запит
        String sql = "INSERT INTO Users(`id`,`login`,`pass`,`name`,`salt`,`avatar`,`email`) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement prep = connection.prepareStatement(sql)) {
            prep.setString(1, user.getId());
            prep.setString(2, user.getLogin());
            prep.setString(3, this.makePasswordHash(user.getPass(), user.getSalt()));
            prep.setString(4, user.getName());
            prep.setString(5, user.getSalt());
            prep.setString(6, user.getAvatar());
            prep.setString(7, user.getEmail());
            prep.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return user.getId();
    }
    /**
     * Updates User to database
     *
     * @param user data to update
     * @return user ID in table
     */
    public String update(User user) {
        User oldUser = getUserById(user.getId());
        String sql = "UPDATE Users SET ";
        String hashPass = makePasswordHash(user.getPass(), user.getSalt());
        if (!oldUser.equals(user)) {
            System.out.println("CHANGED");
            if (validationService.isChanged(oldUser.getLogin(), user.getLogin()))
                sql += String.format("`login` = '%s', ", user.getLogin());
            if (validationService.isChanged(oldUser.getName(), user.getName()))
                sql += String.format("`name` = '%s', ", user.getName());
            if (validationService.isChanged(oldUser.getEmail(), user.getEmail()))
                sql += String.format("`email` = '%s', ", user.getEmail());
            if (validationService.isChanged(oldUser.getPass(), user.getPass()))
                sql += String.format("`pass` = '%s', ", hashPass);
            if (validationService.isChanged(oldUser.getAvatar(), user.getAvatar()))
                sql += String.format("`avatar` = '%s', ", user.getAvatar());
            if (user.getEmailCode() == null) sql += "`email_code` = NULL, ";
            else if (oldUser.getEmailCode() == null && user.getEmailCode() != null ||
                    oldUser.getEmailCode() != null && user.getEmailCode() == null ||
                    !oldUser.getEmailCode().equals(user.getEmailCode())) {
                sql += String.format("`email_code` = '%s', ", user.getEmailCode());
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += String.format(" WHERE id = '%s'", user.getId());
            System.out.println(sql);
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                return null;
            }
        }
        return user.getId();
    }

    /**
     * Checks if login is out from DB table
     *
     * @param login string to test
     * @return true if login NOT in DB
     */
    public boolean isLoginFree(String login) {
        String sql = "SELECT COUNT(u.id) FROM Users u WHERE u.`login` = ? ";
        try (PreparedStatement prep = connection.prepareStatement(sql)) {
            prep.setString(1, login);
            ResultSet res = prep.executeQuery();
            if (res.next()) {
                return res.getInt(1) == 0;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println(sql + "; " + login);
        }
        return false;
    }
    /**
     * Gets User by ID from database
     *
     * @param id user ID in table
     * @return User from table by ID
     */
    public User getUserById(String id) {
        String sql = "SELECT u.* FROM Users u WHERE u.`id` = ?";
        try (PreparedStatement prep = dataService.getConnection().prepareStatement(sql)) {
            prep.setString(1, id);
            ResultSet res = prep.executeQuery();
            if (res.next()) return new User(res);
        } catch (Exception ex) {
            System.out.println("UserDAO::getUserById() " + ex.getMessage()
                    + "\n" + sql + " -- " + id);
        }
        return null;
    }

    /**
     * Looks for user in DB
     *
     * @param login    Credentials: login
     * @param password Credentials: password
     * @return entities.User or null if not found
     */
    public User getUserByCredentials(String login, String password) {
        String sql = "SELECT * FROM Users u WHERE u.`login` = ?";
        try (PreparedStatement prep = connection.prepareStatement(sql)) {
            prep.setString(1, login);
            ResultSet res = prep.executeQuery();
            if (res.next()) {
                User user = new User(res);
                String expectedHash = this.makePasswordHash(password, user.getSalt());
                if (expectedHash.equals(user.getPass())) {
                    return user;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println(sql + "; " + login + " " + password);
        }
        return null;
    }

    public User getUserByCredentialsOld(String login, String password) {
        String sql = "SELECT * FROM Users u WHERE u.`login` = ? AND u.`pass` = ?";
        try (PreparedStatement prep = connection.prepareStatement(sql)) {
            prep.setString(1, login);
            prep.setString(2, this.makePasswordHash(password, ""));
            ResultSet res = prep.executeQuery();
            if (res.next()) {
                return new User(res);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println(sql + "; " + login + " " + password);
        }
        return null;
    }
    /**
     * Makes password hash
     *
     * @param password original user password
     * @param salt user salt
     * @return password hash
     */
    public String makePasswordHash(String password, String salt) {
        return hashService.hash(salt + password + salt);
    }
    /**
     * Confirms email
     *
     * @param user user to confirm the email
     * @param confirm confirm code
     * @return true if confirm is success otherwise false
     */
    public boolean confirmEmail(User user, String confirm) throws Exception {
        if (user == null) {
            throw new Exception("Авторизацію не підтверджено");
        }
        if (user.getEmailCode() == null) {
            throw new Exception("Пошту вже підтверджено");
        }
        if (!user.getEmailCode().equals(confirm)) {
            throw new Exception("Код підтвердження не вірний");
        }
        user.setEmailCode(null);
        return update(user) != null;
    }
}