package ua.karazin.moviesprofileservice.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;

    @Async
    @Override
    public void sendEmail(EmailDetails details) {
        var message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(details.to());
        message.setSubject(details.subject());
        message.setText(details.text());

        sender.send(message);
    }
}
