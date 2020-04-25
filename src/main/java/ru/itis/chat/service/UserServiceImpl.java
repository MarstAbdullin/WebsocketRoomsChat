package ru.itis.chat.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.chat.dto.UserDto;
import ru.itis.chat.models.User;
import ru.itis.chat.repository.UserRepository;


import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final Environment environment;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, Environment environment) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.environment = environment;
    }

    @Override
    public void register(UserDto userDto) {
        if (!userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            userRepository.save(User.builder()
                    .email(userDto.getEmail())
                    .hash(passwordEncoder.encode(userDto.getPassword()))
                    .build());
        }
    }

    @Override
    public void login(UserDto userDto) {
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(userDto.getPassword(), user.getHash())) {
                Algorithm algorithm = Algorithm.HMAC256(environment.getProperty("secret"));
                String token = JWT.create()
                        .withSubject(String.valueOf(user.getId()))
                        .sign(algorithm);
                userDto.setToken(token);
                userDto.setId(user.getId());
            }
        }
    }

    @Override
    public Optional<UserDto> checkToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(environment.getProperty("secret"));
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            Long userId = Long.valueOf(verifier.verify(token).getSubject());
            Optional<UserDto> optionalUser = findUserById(userId);
            if (optionalUser.isPresent()) {
                optionalUser.get().setToken(token);
                return optionalUser;
            } else {
                return Optional.empty();
            }
        } catch (JWTVerificationException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserDto> findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(UserDto::from);
    }
}