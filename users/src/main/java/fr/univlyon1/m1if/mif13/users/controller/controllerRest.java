package fr.univlyon1.m1if.mif13.users.controller;


import fr.univlyon1.m1if.mif13.users.dao.UserDao;
import fr.univlyon1.m1if.mif13.users.model.User;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class controllerRest {

        //private ApplicationContext applicationContext;
        @Autowired
        private UserDao userDao;

    /**
     * Create - Add a new user
     * @Param employee An objet user
     * @return The user object saved
     */
//    @PostMapping("/user")
//    public void createEmployee(@RequestBody User user){
//           userDao.save(user);
//    }

    @GetMapping("/users")
    public Set<String> getUsers(){
        System.out.println("get");
        //userDao = new UserDao();
        return userDao.getAll();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable ("id") final String id){
        Optional<User> user = userDao.get(id);
        if(user.isPresent()){
            return user.get();
        } else {
            return null;
        }
    }

}
