package ua.karazin.moviesprofileservice.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.EntityId;
import ua.karazin.moviesbaseevents.profiles.revision1.ProfileCreatedEvent1;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {
    @EntityId(routingKey = "profileId")
    private String id;

    private String token;

    @EventSourcingHandler
    private void on(ProfileCreatedEvent1 event) {
        this.token = event.confirmationToken();
    }
}
