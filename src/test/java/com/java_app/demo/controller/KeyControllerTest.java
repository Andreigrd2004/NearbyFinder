package com.java_app.demo.controller;

import com.java_app.demo.apikey.KeyController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest(controllers = KeyController.class)
@WithMockUser(roles = {"ADMIN"})
public class KeyControllerTest {

}
