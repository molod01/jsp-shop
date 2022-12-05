package services.email;

public interface EmailService {
    boolean send(String to, String subject, String messageText);
}
