package fr.univlyon1.m1if.mif13.users;

import fr.univlyon1.m1if.mif13.users.dao.UserDao;
import fr.univlyon1.m1if.mif13.users.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
		ApplicationContext ctx =
				new AnnotationConfigApplicationContext(AppConfig.class);
		UserDao userDao = ctx.getBean("getUserDao",UserDao.class);
		User user1 = new User("John", "john@1234");
		User user2 = new User("Mister", "mister@1234");
		userDao.save(user1);
		userDao.save(user2);
		System.out.println(userDao.getAll());
	}

}
