package fr.univlyon1.m1if.mif13.users;

import fr.univlyon1.m1if.mif13.users.dao.UserDao;
import fr.univlyon1.m1if.mif13.users.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@SpringBootApplication
public class UsersApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);

	}

}
