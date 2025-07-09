package com.example.book_library.repository;

import com.example.book_library.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUserByEmail(){
        User user = new User("john@domain.com","John","secretpass");
        userRepository.save(user);

        Optional<User> result = userRepository.findByEmail("john@domain.com");
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("John");
    }

    @Test
    void shouldReturnFalseIfEmailDoesNotExist(){
        Optional<User> user = userRepository.findByEmail("noone@domain.com");
        assertThat(user.isPresent()).isFalse();
    }

    @Test
    void shouldReturnTureIfEmailExists(){
        userRepository.save(new User("exists@domain.com", "Tester", "pwd"));
        Optional<User> user = userRepository.findByEmail("exists@domain.com");
        assertThat(user.isPresent()).isTrue();
    }
}
