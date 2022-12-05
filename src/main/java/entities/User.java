package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class User {
    private String id;
    private String login;
    private String pass;
    private String name;
    private String salt;
    private String avatar;
    private String email;
    private String emailCode;

    public User() {
        setAvatar("24579b36-6276-476f-a1da-e046783420bc.webp");
    }

    public User(ResultSet res) throws SQLException {
        this.setId(res.getString("id"));
        this.setLogin(res.getString("login"));
        this.setEmail(res.getString("email"));
        this.setPass(res.getString("pass"));
        this.setName(res.getString("name"));
        this.setSalt(res.getString("salt"));
        this.setAvatar(res.getString("avatar"));
        this.setEmailCode(res.getString("email_code"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && Objects.equals(login, user.login) && Objects.equals(pass, user.pass) && Objects.equals(name, user.name) && Objects.equals(salt, user.salt) && Objects.equals(avatar, user.avatar) && Objects.equals(email, user.email) && Objects.equals(emailCode, user.emailCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, pass, name, salt, avatar, email, emailCode);
    }

    public String getEmailCode() {
        return emailCode;
    }

    public User setEmailCode(String emailCode) {
        this.emailCode = emailCode;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public User setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPass() {
        return pass;
    }

    public User setPass(String pass) {
        this.pass = pass;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public User setSalt(String salt) {
        this.salt = salt;
        return this;
    }
}
