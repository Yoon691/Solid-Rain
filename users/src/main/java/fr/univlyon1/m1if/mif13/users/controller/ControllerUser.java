package fr.univlyon1.m1if.mif13.users.controller;

import fr.univlyon1.m1if.mif13.users.dao.UserDao;
import fr.univlyon1.m1if.mif13.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.net.UnknownServiceException;
import java.util.Optional;

@Controller
public class ControllerUser {
    @Autowired
    private UserDao userDao;

    @GetMapping(value = "/users", produces = {MediaType.TEXT_HTML_VALUE})
    public String getAll(Model model) {
        model.addAttribute("users", userDao.getAll());
        return "users";
    }

    @GetMapping(value = "/users/{id}", produces = {MediaType.TEXT_HTML_VALUE})
    public String getOne(@PathVariable String id, Model model) {
        Optional<User> user = userDao.get(id);
        model.addAttribute("user", user);
        return "user";
    }

}
