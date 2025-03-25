package ua.karazin.moviesprofileservice.mail;

import org.springframework.lang.NonNull;

public interface EmailService {
    void sendEmail(@NonNull EmailDetails details);
}
