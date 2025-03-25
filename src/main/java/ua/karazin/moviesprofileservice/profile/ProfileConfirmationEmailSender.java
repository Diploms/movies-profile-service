package ua.karazin.moviesprofileservice.profile;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ReplayStatus;
import org.springframework.stereotype.Component;
import ua.karazin.moviesbaseevents.profiles.revision1.ProfileCreatedEvent1;
import ua.karazin.moviesprofileservice.mail.EmailDetails;
import ua.karazin.moviesprofileservice.mail.EmailService;

@Component
@RequiredArgsConstructor
public class ProfileConfirmationEmailSender {
    private static final String SUBJECT = "Profile Confirmation";

    private final EmailService emailService;
    private final ProfileConfirmationEmailTextCreator textCreator;

    @EventHandler
    private void on(ProfileCreatedEvent1 event, ReplayStatus status) {
        if (status.isReplay()) {
            return;
        }

        var recipientEmail = event.dto().email();
        var text = textCreator.createText(event.id(), event.confirmationToken());

        var details = new EmailDetails(recipientEmail, SUBJECT, text);
        emailService.sendEmail(details);
    }
}
