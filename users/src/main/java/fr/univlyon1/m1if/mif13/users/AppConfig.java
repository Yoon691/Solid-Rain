package fr.univlyon1.m1if.mif13.users;

import fr.univlyon1.m1if.mif13.users.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public UserDao getUserDao(){
        UserDao userDao = new UserDao();
        return userDao;
    }

}
