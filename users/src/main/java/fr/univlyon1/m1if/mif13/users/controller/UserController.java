package fr.univlyon1.m1if.mif13.users.controller;

import fr.univlyon1.m1if.mif13.users.dao.UserDao;
import fr.univlyon1.m1if.mif13.users.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.net.UnknownServiceException;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserDao userDao;

    @Operation(
            summary = "Récuperer la liste des uttilisateurs",
            responses = {
        @ApiResponse(responseCode = "200", description = "Operation réussie"),
        @ApiResponse(responseCode = "404", description = "la liste des uttilisateurs est vide", content = @Content())})
    @GetMapping(value = "/users", produces = {MediaType.TEXT_HTML_VALUE})
    public String getAll(Model model) {
        model.addAttribute("users", userDao.getAll());
        return "users";
    }

    @Operation(
            summary = "Récuperer un utilisateur",
            responses = {
                    @ApiResponse(responseCode = "200", description = " Opération réussie"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé", content = @Content())})
    @GetMapping(value = "/users/{login}", produces = {MediaType.TEXT_HTML_VALUE})
    @CrossOrigin(origins = {"http://localhost", "http://192.168.75.22", "https://192.168.75.22"})
    public String getOne(@PathVariable("login") String login, Model model) {
        Optional<User> user = userDao.get(login);
        model.addAttribute("user", user);
        return "user";
    }

}
