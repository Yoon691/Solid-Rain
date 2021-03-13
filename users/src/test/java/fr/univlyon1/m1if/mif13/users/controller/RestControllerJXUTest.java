package fr.univlyon1.m1if.mif13.users.controller;

import fr.univlyon1.m1if.mif13.users.AppConfig;
import fr.univlyon1.m1if.mif13.users.dao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebMvcTest(value = RestControllerJXU.class)
@WebAppConfiguration
class RestControllerJXUTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserDao userDao;
    private MockMvc mockMvc;
    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void createUserEncoded() throws Exception {


    }

    @Test
    void createUserJson() throws Exception {

    }

    @Test
    void getUsers() throws Exception {

    }

    @Test
    void getUser() {
    }

    @Test
    void updateUserUrlEncoded() {
    }

    @Test
    void updateUserJson() {
    }

    @Test
    void deleteUser() {
    }
}