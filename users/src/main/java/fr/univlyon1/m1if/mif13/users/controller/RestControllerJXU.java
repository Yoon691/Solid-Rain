package fr.univlyon1.m1if.mif13.users.controller;


import fr.univlyon1.m1if.mif13.users.dao.UserDao;
import fr.univlyon1.m1if.mif13.users.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class RestControllerJXU {

    @Autowired
    private UserDao userDao;


    /**
     * Creation - Ajouter un  nouveau user
     * @Param user - Un objet de type User
     * @return Une réponse vide avec un code de statut approprié (204, 400, 401).
     */

    @Operation(
            summary = "Créer un  utilisateur",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Utilisateur crée"),
                    @ApiResponse(responseCode = "400", description = "Paramètres de la requête non acceptables",
                            content = @Content()),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non crée",
                            content = @Content())})

    @PostMapping(value = "/users", consumes = {"application/x-www-form-urlencoded","application/json"})
    public ResponseEntity<User> createUser(@RequestBody User user){
        Optional<User> u = userDao.get(user.getLogin());
        if (u.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        userDao.save(user);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

//    /**
//     * Creation - Ajouter un  nouveau user
//     * @Param login - le login  user
//     * @Param password - le password de user
//     * @return Une réponse vide avec un code de statut approprié (204, 400, 401).
//     */
//
//    @Operation(
//            summary = "Créer un  utilisateur",
//            responses = {
//                    @ApiResponse(responseCode = "204", description = "Utilisateur crée"),
//                    @ApiResponse(responseCode = "400", description = "Paramètres de la requête non acceptables",
//                            content = @Content()),
//                    @ApiResponse(responseCode = "401", description = "Utilisateur non crée",
//                            content = @Content())})
//    @PostMapping(value = "/users", consumes ={MediaType.APPLICATION_FORM_URLENCODED_VALUE})
//    public ResponseEntity<User> createUserEncoded(@RequestParam("login") String login, @RequestParam("password") String password, @RequestBody User user/*@RequestBody()@Parameter(required = true, content = @Content(schema = @Schema(implementation = User.class))) MultiValueMap paramMap*/){
//
//            if ( login == null || password == null) {
//                return ResponseEntity.badRequest().build();
//            }
//            //String loginUser = (String) paramMap.get("login");
//            //String passwordUser = (String) paramMap.get("password");
//            User userSave = new User(login, password);
//            user.equals(userSave);
//            userDao.save(user);
//            return ResponseEntity.status(HttpStatus.CREATED).build();
//
//    }



    /**
     * Lire - Get tout les users
     * @Return - Un objet User
     */

    @Operation(
            summary = "Récuperer la liste des uttilisateurs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation réussie"),
                    @ApiResponse(responseCode = "404", description = "la liste des uttilisateurs est vide", content = @Content())})
    @GetMapping(value = "/users",produces = { "application/json","application/xml" })
    public ResponseEntity<Set<String>> getUsers(){
        Set<String> users = userDao.getAll();
        if (users.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    /**
     * Lire - Get un user
     * @Param id - le login de user.
     * @Return Un objet user
     */

    @Operation(
            summary = "Récuperer un utilisateur",
            responses = {
                    @ApiResponse(responseCode = "200", description = " Opération réussie"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé", content = @Content())})
    @GetMapping(value = "/users/{login}",
            produces = { "application/json","application/xml" })
    @CrossOrigin(origins = {"http://localhost", "http://192.168.75.22", "https://192.168.75.22"})
    public ResponseEntity<User> getUser(@PathVariable("login") String login){
        Optional<User> user = userDao.get(login);
        if(user.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Update - Modifier un user existe
     * @param login - Le login de user a modifier
     * @param user - un objet de type user
     * @return  avec un code de statut approprié (204, 400, 404).
     */
    @Operation(
            summary = "Mettre à jour le password de l'utilisateur",
            responses = {
                    @ApiResponse(responseCode = "204", description = " Password correctement modifié",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Paramètres de la requête non acceptables",
                            content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                            content = @Content())})

    @PutMapping(value = "/users/{login}" , consumes = {"application/x-www-form-urlencoded","application/json"})
    private ResponseEntity<User> getUserResponseEntity(@PathVariable("login") String login, @RequestBody User user) {
        if (user.getLogin() == null){
            return ResponseEntity.badRequest().build();
        }
        Optional<User> u = userDao.get(login);
        if (u.isPresent()) {
            User currentUser = u.get();
            if (!currentUser.isConnected()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } else {
                userDao.delete(currentUser);
                userDao.save(user);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }


//
//    /**
//     * Update - Modifier un user existe
//     * @param password - le password de user a modifier
//     * @return  avec un code de statut approprié (204, 400, 404).
//     */
//    @Operation(
//            summary = "Mettre à jour le password de l'utilisateur",
//            responses = {
//                    @ApiResponse(responseCode = "204", description = " Password correctement modifié",
//                            content = @Content(schema = @Schema(implementation = User.class))),
//                    @ApiResponse(responseCode = "400", description = "Paramètres de la requête non acceptables"),
//                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")})
//    @GetMapping(value = "/users/{login}", consumes = {"application/x-www-form-urlencoded"})
//    public ResponseEntity<User> updateUserUrlEncoded(@PathVariable("login") final String login,
//                                                     @RequestParam ("password") String password){
////        if ( password == null) {
////            return ResponseEntity.badRequest().build();
////        }
////        Optional<User> u = userDao.get(login);
////        if (u.isPresent()) {
////            User currentUser = u.get();
////            currentUser.setPassword(password);
////            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
////        }
////        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
////
////    }
//
//
//

    /**
     * Delete - Supprimer  un user
     * @param login - Le id de user a supprimer
     * @return  une reponse vide avec le code de status approprié(204,404)
     */

    @DeleteMapping("users/{login}")
    @Operation(
            summary = "Supprimer  l'utilisateur",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Utilisateur supprimé"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")})
    public ResponseEntity<Void> deleteUser(@PathVariable("login") final String login){
        Optional<User> user = userDao.get(login);
        if(user.isPresent()) {
            User currentUser = user.get();
            if (!currentUser.isConnected()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } else {
                userDao.delete(currentUser);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}
