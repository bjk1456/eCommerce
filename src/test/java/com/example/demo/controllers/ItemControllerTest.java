package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {
    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp(){
        itemController = new ItemController(itemRepository);
    }

    @Test
    public void findUserNameSuccessfully() throws Exception {
        Item hammer = new Item();
        hammer.setId(1l);
        hammer.setPrice(BigDecimal.valueOf(23.222));
        hammer.setName("Hammer");
        List<Item> itemL = new ArrayList();
        itemL.add(hammer);
        when(itemRepository.findByName("Hammer")).thenReturn(itemL);
        itemController.getItemsByName("Hammer");
        List<Item> items = itemRepository.findByName("Hammer");
        Item foundI = items.get(0);
        assertEquals(foundI.getName(), "Hammer");
        assertEquals(foundI.getPrice().toString(),"23.222");
        assertEquals(1l, 1l);
    }

    @Test
    public void findUserIdSuccessfully() throws Exception {
        Item hammer = new Item();
        hammer.setId(1l);
        hammer.setPrice(BigDecimal.valueOf(23.222));
        hammer.setName("Hammer");
        when(itemRepository.findById(1l)).thenReturn(java.util.Optional.of(hammer));
        Item foundI = itemRepository.findById(1l).get();
        assertEquals(foundI.getName(), "Hammer");
        assertEquals(foundI.getPrice().toString(),"23.222");
        assertEquals(1l, 1l);
    }
}