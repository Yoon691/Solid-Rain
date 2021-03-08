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
public class ControllerRest {


    @Autowired
    private UserDao userDao;

    /**
     * Creation - Ajouter un  nouveau user
     * @Param User Un objet user
     * esseyer de changer le type de retour avec ResponseEntity et tester avec badRequest
     * sinin utilise @RequestMapping avec methode Post
     */
    @PostMapping("/user")
    public User createUser(@RequestBody User user){
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
        return userDao.getAll();
    }

    /**
     * Lire - Get un user
     * @Param Id le login de user.
     * @Return Un objet user
     */
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") String id){
        Optional<User> user = userDao.get(id);
        return user.orElse(null);
    }

    /**
     * Update - Modifier un user existe
     * @param id - Le login de user a modifier
     * @param user - le objet user a modifier
     *Pareil pour que le post
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
     * Delete - Supprimer  un user
     * @param id - Le id de user a supprimer
     */

    @DeleteMapping("user/{id}")
    public void deleteUser(@PathVariable("id") final String id){
        Optional<User> u = userDao.get(id);
        User currentUser = u.get();
        userDao.delete(currentUser);
    }


}
