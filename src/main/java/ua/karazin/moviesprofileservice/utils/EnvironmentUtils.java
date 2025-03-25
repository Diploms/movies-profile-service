package ua.karazin.moviesprofileservice.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
@RequiredArgsConstructor
public class EnvironmentUtils implements ApplicationListener<WebServerInitializedEvent> {
    private final Environment environment;

    @Getter
    private int port;

    public String getHost() {
        return InetAddress.getLoopbackAddress().getCanonicalHostName();
    }

    public String getFullHostWithProtocol() {
        return "http://" + getHost() + ":" + port;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.port = event.getWebServer().getPort();
    }
}
