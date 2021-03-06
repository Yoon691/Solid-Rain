package fr.univlyon1.m1if.mif13.users.dao;

import fr.univlyon1.m1if.mif13.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.*;


public class UserDao implements Dao<User> {

    private List<User> users = new ArrayList<>();



    /**
     * Récupère un utilisateur
     *
     * @param id Login de l'utilisateur
     * @return Un java.util.Optional qui contient (ou pas) l'utilisateur
     */
    @Override
    public Optional<User> get(String id) {
        return Optional.empty();
    }

    /**
     * Récupère tous les utilisateurs
     *
     * @return Un Set de login
     */
    @Override
    public Set<String> getAll() {
        Set<String> loginUsers = new HashSet<>();
        for(int i = 0; i < users.size(); i++){
            loginUsers.add(users.get(i).getLogin());
        }
        System.out.println("getAll");
        return loginUsers;
    }

    /**
     * Crée un utilisateur et le sauvegarde
     *
     * @param user L'utilisateur à créer
     */
    @Override
    public void save(User user) {
        users.add(user);
    }

    /**
     * Modifie un utilisateur
     *
     * @param user   L'utilisateur à modifier
     * @param params Un tableau de **2** Strings : login et password
     */
    @Override
    public void update(User user, String[] params) {
        user.setLogin(Objects.requireNonNull(
                params[0], "Login cannot be null"));
        user.setPassword(Objects.requireNonNull(
                params[1], "Password cannot be null"));
        users.add(user);

    }

    /**
     * Supprime un utilisateur
     *
     * @param user L'utilisateur à supprimer
     */
    @Override
    public void delete(User user) {
        users.remove(user);
    }
}
