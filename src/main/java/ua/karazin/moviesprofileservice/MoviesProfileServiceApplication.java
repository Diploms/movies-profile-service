package ua.karazin.moviesprofileservice;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class MoviesProfileServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoviesProfileServiceApplication.class, args);
    }
}
