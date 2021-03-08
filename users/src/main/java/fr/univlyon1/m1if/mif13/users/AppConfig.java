package fr.univlyon1.m1if.mif13.users;

import fr.univlyon1.m1if.mif13.users.dao.UserDao;
import fr.univlyon1.m1if.mif13.users.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class AppConfig {
    @Bean()
    @Scope(value = "singleton")
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        User user = new User("Younes", "Derbal");
        User user1 = new User("John", "john1234");
        userDao.save(user);
        userDao.save(user1);
        return userDao;

    }
}

