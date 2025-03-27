package ua.karazin.moviesprofileservice.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import ua.karazin.moviesbaseevents.profiles.revision1.*;

import java.time.Instant;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @AggregateIdentifier
    private String id;

    @Email
    @NotNull
    private String email;

    @NotBlank
    private String name;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;

    private boolean isConfirmed;

    @AggregateMember
    private ConfirmationToken confirmationToken;

    @CommandHandler
    private Profile(CreateProfileCommand1 command) {
        apply(new ProfileCreatedEvent1(command.profileId(), command.dto(), UUID.randomUUID().toString()));
    }

    @CommandHandler
    private void handle(UpdateProfileCommand1 command) {
        apply(new ProfileUpdatedEvent1(command.id(), command.dto()));
    }

    @CommandHandler
    private void handle(ConfirmProfileCommand1 command) {
        if (this.isConfirmed) {
            throw new CommandExecutionException("Profile is already confirmed", new IllegalStateException());
        }

        var actualTokenValue = this.confirmationToken.getToken();
        var expectedTokenValue = command.validationToken();

        if (!actualTokenValue.equals(expectedTokenValue)) {
            throw new CommandExecutionException("Wrong token value", new IllegalStateException());
        }

        apply(new ProfileConfirmedEvent1(command.profileId()));
    }

    @EventSourcingHandler
    private void on(ProfileCreatedEvent1 event, EventMessage<?> message) {
        this.id = event.id();
        this.name = event.dto().name();
        this.email = event.dto().email();
        this.createdAt = message.getTimestamp();
        this.updatedAt = message.getTimestamp();
        this.confirmationToken = new ConfirmationToken();
    }

    @EventSourcingHandler
    private void on(ProfileUpdatedEvent1 event, EventMessage<?> message) {
        this.name = event.dto().name();
        this.email = event.dto().email();
        this.updatedAt = message.getTimestamp();
    }

    @EventSourcingHandler
    private void on(ProfileConfirmedEvent1 event, EventMessage<?> message) {
        this.isConfirmed = true;
        this.updatedAt = message.getTimestamp();
    }
}
