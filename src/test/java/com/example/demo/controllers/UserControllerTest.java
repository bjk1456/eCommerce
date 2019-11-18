package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.security.JwtTokenProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserRepository userRepository = mock(UserRepository.class);
    @Mock
    private CartRepository cartRepository = mock(CartRepository.class);
    @Mock
    private JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
    @Mock
    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    @Before
    public void setUp(){
        userController = new UserController(userRepository, cartRepository, jwtTokenProvider);
    }

    @Test
    public void createUserSuccessfully() throws Exception{
        List<String> roles = new ArrayList<String>();
        roles.add("ROLE_CLIENT");
        when(jwtTokenProvider.createToken("Fidel",roles)).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCYXJ0IiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9DTElFTlQifV0sImlhdCI6MTU3NDEwMzU2MSwiZXhwIjoxNTc0MTA3MTYxfQ.9BE-iYKN2Rufpvf6xd9JD1KXdzpA-hRodg22ByA-DK8");
        CreateUserRequest req = new CreateUserRequest();
        req.setUsername("Fidel");
        req.setPassword("CleverSecret");

        String response = userController.createUser(req);
        assertNotNull(response);
    }

    @Test
    public void findUserNameSuccessfully() throws Exception{
        User user = new User();
        user.setUsername("Bill");
        user.setPassword("VerySecret");
        userRepository.save(user);
        when(userRepository.findByUsername("Bill")).thenReturn(user);

        ResponseEntity<User> foundUser = userController.findByUserName("Bill");
        assertEquals(200,foundUser.getStatusCodeValue());
        assertEquals("Bill", foundUser.getBody().getUsername());
    }

    @Test
    public void findUserIdSuccessfully() throws Exception{
        User user = new User();
        user.setUsername("Bill");
        user.setPassword("VerySecret");
        user.setId(0);
        userRepository.save(user);
        when(userRepository.findById(0l)).thenReturn(java.util.Optional.of(user));

        ResponseEntity<User> foundUser = userController.findById(0l);
        assertEquals(200,foundUser.getStatusCodeValue());
        assertEquals("Bill", foundUser.getBody().getUsername());
    }



}
