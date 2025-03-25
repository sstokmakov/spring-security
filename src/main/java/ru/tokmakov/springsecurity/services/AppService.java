package ru.tokmakov.springsecurity.services;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tokmakov.springsecurity.models.Application;
import ru.tokmakov.springsecurity.models.User;
import ru.tokmakov.springsecurity.repository.UserRepository;

import java.util.List;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class AppService {
    private List<Application> applications;
    private final UserRepository repository;
    private PasswordEncoder encoder;

    @PostConstruct
    public void loadAppInDB() {
        Faker faker = new Faker();
        applications = IntStream.range(1, 100)
                .mapToObj(i -> Application.builder()
                        .id(i)
                        .name(faker.app().name())
                        .author(faker.app().author())
                        .version(faker.app().version())
                        .build()
                ).toList();
    }

    public List<Application> allApplications() {
        return applications;
    }

    public Application applicationById(int id) {
        return applications.stream()
                .filter(el -> el.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addIUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
    }
}