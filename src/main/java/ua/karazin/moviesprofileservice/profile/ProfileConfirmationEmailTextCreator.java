package ua.karazin.moviesprofileservice.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import ua.karazin.moviesprofileservice.utils.EnvironmentUtils;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProfileConfirmationEmailTextCreator {
    private final EnvironmentUtils environmentUtils;

    public String createText(String id, String token) {
        return "Confirmation link:\n" + createConfirmationLink(id, token);
    }

    private URI createConfirmationLink(String id, String token) {
        return UriComponentsBuilder.fromUriString(environmentUtils.getFullHostWithProtocol() + "/profile/confirm")
                .queryParam("id", id)
                .queryParam("token", token)
                .build().toUri();
    }
}
