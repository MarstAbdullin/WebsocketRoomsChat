package ru.itis.chat;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.chat.config.ApplicationContextConfig;
import ru.itis.chat.models.User;
import ru.itis.chat.repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);

        UserRepository repository = context.getBean(UserRepository.class);
        repository.save(User.builder().email("dfsg").hash("dfgfxgd").build());
    }
}
