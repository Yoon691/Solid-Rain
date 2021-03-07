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


    @Autowired
    private UserDao userDao;

    /**
     * Create - Add a new user
     * @Param User An objet user
     *
     */
    @PostMapping("/user")
    public User createUser(@RequestBody User user){
        System.out.println("post");
        User userSave = new User(user.getLogin(), user.getPassword());
        userDao.save(userSave);
        return userSave;
    }

    /**
     * Lire - Get tout les users
     * @Return - Un objet User
     */
    @GetMapping("/users")
    public Set<String> getUsers(){
        System.out.println("get");
        //userDao = new UserDao();
        return userDao.getAll();
    }

    /**
     * Read - Get one user
     * @Param Id The login of user.
     * @Return An user object full filled
     */
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") String id){
        System.out.println(id);
        Optional<User> user = userDao.get(id);
        return user.orElse(null);
    }

    /**
     * Update - Update an existing user
     * @param id - The login of the employee to update
     * @param user - The user object updated
     *
     */

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable("id") final String id, @RequestBody User user){
        Optional<User> u = userDao.get(id);
        if (u.isPresent()){
            User currentUser = u.get();

            String login = user.getLogin();
            if (login != null) {
                currentUser.setLogin(login);
            }
            String password = user.getPassword();
            if ( password != null) {
                currentUser.setPassword(password);
            }

            return currentUser;
        } else {
            return null;
        }
    }

    /**
     * Delete - Delete an user
     * @param id - The id of user to delete
     */

    @DeleteMapping("user/{id}")
    public void deleteUser(@PathVariable("id") final String id){
        Optional<User> u = userDao.get(id);
        User currentUser = u.get();
        userDao.delete(currentUser);
    }


}
