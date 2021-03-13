package fr.univlyon1.m1if.mif13.users.controller;

import fr.univlyon1.m1if.mif13.users.dao.UserDao;
import fr.univlyon1.m1if.mif13.users.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
@ResponseBody
public class OperationController {

    @Autowired
    private UserDao userDao;

    /**
     * Procédure de login "simple" d'un utilisateur
     * @param login Le login de l'utilisateur. L'utilisateur doit avoir été créé préalablement et son login doit être présent dans le DAO.
     * @param password Le password à vérifier.
     * @return Une ResponseEntity avec le JWT dans le header "Authentication" si le login s'est bien passé, et le code de statut approprié (204, 401 ou 404).
     */

    @Operation(
            summary = "Connexion utilisateur",
            responses = {
                    @ApiResponse(responseCode = "204",
                                 description = "Utilisateur connecté",
                                 headers = @Header(name = HttpHeaders.AUTHORIZATION, description = "token")),
                    @ApiResponse(responseCode = "400", description = "Paramètres de la requête non acceptables"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")})
    @PostMapping("/login")
    @CrossOrigin(origins = {"http://localhost", "http://192.168.75.22",  "https://192.168.75.22"}, exposedHeaders = HttpHeaders.AUTHORIZATION)
    public ResponseEntity<Void> login(@RequestParam("login") String login,
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


    /**
     * Procédure déconnexion
     * @param login de l'utilisateur à déconnecté
     * @return Une réponse vide avec un code de statut approprié (204, 401).
     */

    @Operation(
            summary = "Deconnexion de l'utilisateur connecté",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Utilisateur deconecté",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")})
    @DeleteMapping( "/logout")
    @CrossOrigin(origins = {"http://localhost", "http://192.168.75.22", "https://192.168.75.22"})
    public ResponseEntity<Void> logout(@RequestParam("login") String login){

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


    /**
     * Méthode destinée au serveur Node pour valider l'authentification d'un utilisateur.
     * @param token Le token JWT qui se trouve dans le header "Authentication" de la requête
     * @param origin L'origine de la requête (pour la comparer avec celle du client, stockée dans le token JWT)
     * @return Une réponse vide avec un code de statut approprié (204, 400, 401).
     *
     */
    @Operation(
            summary = "Authentification utilisateur",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Utilisateur authentifé",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Paramètres de la requête non acceptables"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")})
    @GetMapping("/authenticate")
    @CrossOrigin(origins = {"http://localhost", "http://192.168.75.22",  "https://192.168.75.22"})
    public ResponseEntity<Void> authenticate(@RequestParam("token") String token,
                                             @RequestParam("origin") String origin)
    {
        if (token == null || origin == null) {
            return ResponseEntity.badRequest().build();
        }

        String authenticateToken = verifyToken(token, origin);
        Optional<User> userOptional = userDao.get(authenticateToken);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (userOptional.get().isConnected()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();



    }


}
