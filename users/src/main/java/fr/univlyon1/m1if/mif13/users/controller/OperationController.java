package fr.univlyon1.m1if.mif13.users.controller;

import fr.univlyon1.m1if.mif13.users.dao.UserDao;
import fr.univlyon1.m1if.mif13.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Optional;

import static fr.univlyon1.m1if.mif13.users.utils.JwtHelper.generateToken;
import static fr.univlyon1.m1if.mif13.users.utils.JwtHelper.verifyToken;

@Controller
public class OperationController {

    @Autowired
    private UserDao userDao;

    /**
     * Procédure de login "simple" d'un utilisateur
     * @param login Le login de l'utilisateur. L'utilisateur doit avoir été créé préalablement et son login doit être présent dans le DAO.
     * @param password Le password à vérifier.
     * @return Une ResponseEntity avec le JWT dans le header "Authentication" si le login s'est bien passé, et le code de statut approprié (204, 401 ou 404).
     */
    @RequestMapping(value = "/login",
            produces = { "application/json", "application/xml" },
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<Void> loginUrlEncoded(@RequestParam("login") String login,
                                                @RequestParam("password") String password,
                                                @RequestHeader("Origin") String origin){
        if (login == null || password == null) {
        return ResponseEntity.badRequest().build();
    }
    User user = new User(login, password);

    Optional<User> userOptional = userDao.get(user.getLogin());
        if (userOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
        try {
        userOptional.get().validatePassword(password);
    } catch (AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    String authorizationToken = generateToken(user.getLogin(), origin);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .header("Authorization", "Bearer ".concat(authorizationToken)).build();
}

    @RequestMapping(value = "/login",
            produces = { "application/json", "application/xml" },
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> loginJson(@RequestBody User userRequest,
                                          @RequestHeader("Origin") String origin){
        if (userRequest.getLogin() == null || userRequest.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }
        User user = new User(userRequest.getLogin(), userRequest.getPassword());

        Optional<User> userOptional = userDao.get(user.getLogin());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            userOptional.get().validatePassword(userRequest.getPassword());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String authorizationToken = generateToken(user.getLogin(), origin);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header("Authorization", "Bearer ".concat(authorizationToken)).build();
    }

    /**
     * Réalise la déconnexion
     * Ne marche pas urlEncode
     */
    @DeleteMapping(value = "/logout" ,consumes = {"application/x-www-form-urlencoded"})
    public ResponseEntity<Void> logoutUrlEncoded(@RequestParam("login") String login){

        if (login == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> userOptional = userDao.get(login);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (userOptional.get().isConnected()){
            userOptional.get().disconnect();
        }
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/logout" ,consumes = {"application/json"})
    public ResponseEntity<Void> logoutJson(@RequestBody User userRequest){

        if (userRequest.getLogin() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> userOptional = userDao.get(userRequest.getLogin());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (userOptional.get().isConnected()){
            userOptional.get().disconnect();
        }
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /**
     * Méthode destinée au serveur Node pour valider l'authentification d'un utilisateur.
     * @param token Le token JWT qui se trouve dans le header "Authentication" de la requête
     * @param origin L'origine de la requête (pour la comparer avec celle du client, stockée dans le token JWT)
     * @return Une réponse vide avec un code de statut approprié (204, 400, 401).
     * ajouter le status 400
     */
    @PostMapping(value = "/authenticate",
            produces = { "application/json", "application/xml" },
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<Void> authenticateUrlEncoded(@RequestParam("token") String token,
                                             @RequestParam("origin") String origin)
    {
        if (token == null || origin == null) {
            return ResponseEntity.badRequest().build();
        }

        String authenticateToken = verifyToken(token, origin);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).header("authenticate",authenticateToken).build();
    }

    @PostMapping(value = "/authenticate",
            produces = { "application/json", "application/xml" },
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> authenticateJson(@RequestBody String tokenJson,
                                                 @RequestHeader("origin") String origin)
    {
        if (tokenJson == null || origin == null) {
            return ResponseEntity.badRequest().build();
        }
        String authenticateToken = verifyToken(tokenJson, origin);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).header("authenticate",authenticateToken).build();
    }


}
