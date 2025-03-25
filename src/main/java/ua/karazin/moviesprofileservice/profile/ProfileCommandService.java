package ua.karazin.moviesprofileservice.profile;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import ua.karazin.moviesbaseevents.profiles.revision1.ConfirmProfileCommand1;
import ua.karazin.moviesbaseevents.profiles.revision1.CreateProfileCommand1;
import ua.karazin.moviesbaseevents.profiles.revision1.ProfileDto1;
import ua.karazin.moviesbaseevents.profiles.revision1.UpdateProfileCommand1;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProfileCommandService {
    private final CommandGateway commandGateway;

    public CompletableFuture<ProfileDto1> createProfile(ProfileDto1 dto) {
        return commandGateway.send(new CreateProfileCommand1(UUID.randomUUID().toString(), dto, UUID.randomUUID().toString()));
    }

    public CompletableFuture<ProfileDto1> updateProfile(ProfileDto1 dto) {
        return commandGateway.send(new UpdateProfileCommand1(dto.id(), dto));
    }

    public CompletableFuture<ProfileDto1> confirmProfile(String profileId, String token) {
        return commandGateway.send(new ConfirmProfileCommand1(profileId, token));
    }
}
