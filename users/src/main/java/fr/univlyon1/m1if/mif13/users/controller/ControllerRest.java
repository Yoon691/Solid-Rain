package fr.univlyon1.m1if.mif13.users.controller;


import fr.univlyon1.m1if.mif13.users.dao.UserDao;
import fr.univlyon1.m1if.mif13.users.model.User;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.util.Optional;
import java.util.Set;

@RestController
public class ControllerRest {


    @Autowired
    private UserDao userDao;

    /**
     * Creation - Ajouter un  nouveau user
     * @Param login - le login  user
     * @Param password - le password de user
     * @return Une réponse vide avec un code de statut approprié (204, 400, 401).
     */
    //value = "/users", consumes = {"application/json","application/x-www-form-urlencoded"}
    @PostMapping(value = "/user", consumes = {"application/x-www-form-urlencoded"})
    public ResponseEntity<User> createUserEncoded(@RequestParam("login") String login, @RequestParam ("password") String password){
        try {

            if (login == null || password == null) {
                System.out.println("PostCreation");
                return ResponseEntity.badRequest().build();
            }
            User userSave = new User(login, password);
            userDao.save(userSave);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (AuthenticationException exc){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foo Not Found", exc);
        }
    }

    @PostMapping(value = "/user", consumes = {"application/json"})
    public ResponseEntity<User> createUserJson(@RequestBody User user){
        if (user.getLogin() == null || user.getPassword() == null) {
            System.out.println("PostCreation");
            return ResponseEntity.badRequest().build();
        }
        User userSave = new User(user.getLogin(), user.getPassword());
        userDao.save(userSave);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Lire - Get tout les users
     * @Return - Un objet User
     */
//    @GetMapping(value = "/users",
//                produces = { "application/json","application/xml" })
    @RequestMapping(
            value = "/users",
            produces = { "application/json","application/xml" },
            method = RequestMethod.GET)
    @ResponseBody
    public Set<String> getUsers(){
        return userDao.getAll();
    }

    /**
     * Lire - Get un user
     * @Param id - le login de user.
     * @Return Un objet user
     * que produce comme le get précedent
     */
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") String id){
        Optional<User> user = userDao.get(id);
        return user.orElse(null);
    }

    /**
     * Update - Modifier un user existe
     * @param id - Le login de user a modifier
     * @param login - le login de user a modifier
     * @param password - le password de user a modifier
     *Marche mais N probléme dans le if il faut le régler car il faut le deux param pour
     * modifier le user mais a la base avec on peut changer que un seul
     *     consumer le put comme le post de creation
     */

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") final String id, @RequestParam("login") String login, @RequestParam ("password") String password){
        if ((login == null) & (password == null)) {
            System.out.println("PostCreation");
            return ResponseEntity.badRequest().build();
        }
            Optional<User> u = userDao.get(id);
            if (u.isPresent()) {
                User currentUser = u.get();

                if (login != null) {
                    currentUser.setLogin(login);
                }

                if (password != null) {
                    currentUser.setPassword(password);
                }

                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
