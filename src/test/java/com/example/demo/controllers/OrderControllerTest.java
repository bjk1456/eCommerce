package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    @InjectMocks
    private OrderController orderController;

    @Mock
    private UserRepository userRepository = mock(UserRepository.class);

    @Mock
    private OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setUp(){

        orderController = new OrderController(userRepository, orderRepository);
    }

    @Test
    public void submitOrderSuccessfully() throws Exception{
        User user = new User();
        user.setUsername("Bill");
        user.setPassword("VerySecret");
        userRepository.save(user);
        Cart savedCart = new Cart();
        savedCart.setId(1l);
        savedCart.setUser(user);
        savedCart.setTotal(BigDecimal.valueOf(23.222));
        List<Item> itemL = new ArrayList<>();
        Item hammer = new Item();
        hammer.setId(1l);
        hammer.setName("Hammer");
        itemL.add(hammer);
        savedCart.setItems(itemL);
        UserOrder order = new UserOrder();
        order.setItems(itemL);
        when(orderRepository.save(order)).thenReturn(order);
        orderController.submit(user.getUsername());
        UserOrder savedO = orderRepository.save(order);
        Item foundH = savedO.getItems().get(0);
        assertEquals(foundH.getName(),"Hammer");
        assertEquals(foundH.getId().toString(), "1");

    }

    @Test
    public void getOrderHistory() throws Exception{
        User user = new User();
        user.setUsername("Bill");
        user.setPassword("VerySecret");
        userRepository.save(user);
        Cart savedCart = new Cart();
        savedCart.setId(1l);
        savedCart.setUser(user);
        savedCart.setTotal(BigDecimal.valueOf(23.222));
        List<Item> itemL = new ArrayList<>();
        Item hammer = new Item();
        hammer.setId(1l);
        hammer.setName("Hammer");
        hammer.setPrice(BigDecimal.valueOf(23.222));
        itemL.add(hammer);
        savedCart.setItems(itemL);
        UserOrder order = new UserOrder();
        order.setItems(itemL);
        UserOrder userOr = new UserOrder();
        userOr.setItems(itemL);
        userOr.setUser(user);
        List<UserOrder> orders = new ArrayList<>();
        orders.add(userOr);

        when(orderRepository.findByUser(user)).thenReturn(orders);
        orderController.submit(user.getUsername());
        List<UserOrder> userOrders = orderRepository.findByUser(user);
        UserOrder userOrder = userOrders.get(0);
        Item userOItem = userOrder.getItems().get(0);
        assertEquals(userOrder.getUser().getUsername(), "Bill");
        assertEquals(userOItem.getName(), "Hammer");
        assertEquals(userOItem.getPrice().toString(), "23.222");
    }
}
