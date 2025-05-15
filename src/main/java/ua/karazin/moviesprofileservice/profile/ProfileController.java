package ua.karazin.moviesprofileservice.profile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.karazin.moviesbaseevents.profiles.revision1.ProfileDto1;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileCommandService profileCommandService;

    @PostMapping("/confirm")
    public CompletableFuture<ProfileDto1> confirmProfile(@RequestParam String id,
                                                         @RequestParam String token) {
        return profileCommandService.confirmProfile(id, token);
    }

    @PostMapping
    public CompletableFuture<ProfileDto1> createProfile(@RequestBody @Valid ProfileDto1 dto) {
        return profileCommandService.createProfile(dto);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ProfileDto1> updateProfile(@PathVariable UUID id,
                                                        @RequestBody @Valid ProfileDto1 dto) {
        return profileCommandService.updateProfile(dto);
    }
}
