package fr.univlyon1.m1if.mif13.users.controller;


import fr.univlyon1.m1if.mif13.users.dao.UserDao;
import fr.univlyon1.m1if.mif13.users.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.AuthenticationException;
import java.util.Map;
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

    @Operation(
            summary = "Créer un  utilisateur",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Utilisateur crée",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Paramètres de la requête non acceptables",
                            content = @Content()),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non crée",
                            content = @Content())})
    @PostMapping(value = "/users", consumes = {"application/x-www-form-urlencoded"})
    public ResponseEntity<User> createUserEncoded(@RequestParam("login") String login, @RequestParam ("password") String password){

            if (login == null || password == null) {
                return ResponseEntity.badRequest().build();
            }
            User userSave = new User(login, password);
            userDao.save(userSave);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


    @Operation(
            summary = "Créer un utilisateur",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Utilisateur crée"
                            /*content = @Content(schema = @Schema(implementation = User.class))*/),
                    @ApiResponse(responseCode = "400", description = "Paramètres de la requête non acceptables" ,
                                  content = @Content()),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non crée", content = @Content())})
    @PostMapping(value = "/users", consumes = {"application/json"})
    public ResponseEntity<User> createUserJson(@RequestBody Map<String,String> param){
        if (param.get("login") == null || param.get("password") == null) {
            return ResponseEntity.badRequest().build();
        }
        User userSave = new User(param.get("login"), param.get("password"));
        userDao.save(userSave);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Lire - Get tout les users
     * @Return - Un objet User
     */
    @GetMapping(value = "/users",produces = { "application/json","application/xml" })
    @Operation(
            summary = "Récuperer la liste des uttilisateurs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation réussie",
                            content = @Content(schema = @Schema(implementation = User.class))),
                   })
    public Set<String>getUsers(){
        return userDao.getAll();
    }

//    @GetMapping(value = "/users", produces = {MediaType.TEXT_HTML_VALUE})
//    public ModelAndView getAll(Model model) {
//        ModelAndView modelAndView = new ModelAndView();
//        model.addAttribute("users", this.userDao.getAll());
//        modelAndView.setViewName("users");
//        return modelAndView;
//    }
    /**
     * Lire - Get un user
     * @Param id - le login de user.
     * @Return Un objet user
     */

    @Operation(
            summary = "Récuperer un utilisateur",
            responses = {
                    @ApiResponse(responseCode = "200", description = " Opération réussie",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")})
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
     * @param password - le password de user a modifier
     * @return  avec un code de statut approprié (204, 400, 404).
     */

    @PutMapping(value = "/users/{login}", consumes = {"application/x-www-form-urlencoded"})
    @Operation(
            summary = "Mettre à jour le password de l'utilisateur",
            responses = {
                    @ApiResponse(responseCode = "204", description = " Password correctement modifié",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Paramètres de la requête non acceptables"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")})
    public ResponseEntity<User> updateUserUrlEncoded(@PathVariable("login") final String login,
                                                     @RequestParam ("password") String password)
    {
        return getUserResponseEntity(login, password);
    }


    @PutMapping(value = "/users/{login}" , consumes = {"application/json"})
    @Operation(
            summary = "Mettre à jour le password de l'utilisateur",
            responses = {
                    @ApiResponse(responseCode = "204", description = " Password correctement modifié",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Paramètres de la requête non acceptables"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")})
    public ResponseEntity<User> updateUserJson(@PathVariable("login") final String login,
                                               @RequestBody String password)
    {
        return getUserResponseEntity(login, password);
    }

    private ResponseEntity<User> getUserResponseEntity(@PathVariable("login") String login, @RequestBody String password) {
        if ( password == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<User> u = userDao.get(login);
        if (u.isPresent()) {
            User currentUser = u.get();
            currentUser.setPassword(password);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    /**
     * Delete - Supprimer  un user
     * @param login - Le id de user a supprimer
     * @return  une reponse vide avec le code de status approprié(204,404)
     */

    @DeleteMapping("users/{login}")
    @Operation(
            summary = "Supprimer  l'utilisateur",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Utilisateur supprimé",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")})
    public ResponseEntity<Void> deleteUser(@PathVariable("login") final String login){
        Optional<User> user = userDao.get(login);
        if(user.isPresent()) {
            User currentUser = user.get();
            userDao.delete(currentUser);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}
