package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    @InjectMocks
    private CartController cartController;

    @Mock
    private UserRepository userRepository = mock(UserRepository.class);

    @Mock
    private CartRepository cartRepository = mock(CartRepository.class);

    @Mock
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp(){
        cartController = new CartController(userRepository, cartRepository, itemRepository);
    }

    @Test
    public void addItemSuccessfully() throws Exception {
        ModifyCartRequest req = new ModifyCartRequest();
        User user = new User();
        user.setUsername("Bill");
        user.setPassword("VerySecret");
        userRepository.save(user);
        when(userRepository.findByUsername("Bill")).thenReturn(user);
        User foundU = userRepository.findByUsername("Bill");
        req.setItemId(1);
        req.setQuantity(2);
        cartController.addTocart(req);

        Cart savedCart = new Cart();
        savedCart.setId(1l);
        savedCart.setUser(foundU);
        savedCart.setTotal(BigDecimal.valueOf(23.222));
        List<Item> itemL = new ArrayList<>();
        Item hammer = new Item();
        hammer.setId(req.getItemId());
        hammer.setName("Hammer");
        itemL.add(hammer);
        savedCart.setItems(itemL);

        when(cartRepository.save(savedCart)).thenReturn(savedCart);
        Cart foundCart = cartRepository.save(savedCart);
        Item foundH = foundCart.getItems().get(0);
        assertEquals(foundH.getName(), "Hammer");
        assertEquals(foundH.getId().toString(), "1");
    }

    @Test
    public void removeItemSuccessfully() throws Exception {
        ModifyCartRequest req = new ModifyCartRequest();
        User user = new User();
        user.setUsername("Bill");
        user.setPassword("VerySecret");
        userRepository.save(user);
        when(userRepository.findByUsername("Bill")).thenReturn(user);
        User foundU = userRepository.findByUsername("Bill");
        req.setItemId(1);
        req.setQuantity(2);
        cartController.addTocart(req);

        Cart savedCart = new Cart();
        savedCart.setId(1l);
        savedCart.setUser(foundU);
        savedCart.setTotal(BigDecimal.valueOf(23.222));
        List<Item> itemL = new ArrayList<>();
        Item hammer = new Item();
        hammer.setId(req.getItemId());
        hammer.setName("Hammer");
        hammer.setPrice(BigDecimal.valueOf(23.222));
        itemL.add(hammer);
        savedCart.setItems(itemL);

        when(cartRepository.save(savedCart)).thenReturn(savedCart);
        Cart foundCart = cartRepository.save(savedCart);
        Item foundH = foundCart.getItems().get(0);
        assertEquals(foundH.getName(), "Hammer");
        assertEquals(foundH.getId().toString(), "1");

        ModifyCartRequest reqRem = new ModifyCartRequest();
        reqRem.setItemId(1l);
        savedCart.removeItem(hammer);
        cartController.removeFromcart(req);
        when(cartRepository.save(savedCart)).thenReturn(savedCart);
        Cart empty = cartRepository.save(savedCart);

        List<Item> emptyL = new ArrayList<>();
        assertEquals(empty.getItems(), emptyL);
    }


}
