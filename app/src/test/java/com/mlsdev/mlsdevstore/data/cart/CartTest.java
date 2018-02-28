package com.mlsdev.mlsdevstore.data.cart;

import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.data.model.item.Price;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CartTest {

    private Cart cart;
    private Item item;
    private Price price;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        cart = new Cart();
        item = new Item();
        price = new Price();
        price.setCurrency("USD");
        price.setValue(100d);
        item.setItemId("id");
        item.setTitle("title");
        item.setCondition(Item.Condition.New);
        item.setPrice(price);
    }

    @Test
    public void addItem() {
        cart.addItem(item);
        assertEquals(1, cart.getItems().size());
        assertEquals(item.getId(), cart.getItems().get(0).getId());
        assertEquals(item.getTitle(), cart.getItems().get(0).getTitle());
        assertEquals(item.getCondition(), cart.getItems().get(0).getCondition());
        assertEquals(item.getPrice().getCurrency(), cart.getItems().get(0).getPrice().getCurrency());
        assertEquals(item.getPrice().getValue(), cart.getItems().get(0).getPrice().getValue(), 0);
    }

    @Test
    public void removeItem() {
        cart.addItem(item);
        assertFalse(cart.getItems().isEmpty());

        cart.removeItem(item.getId());
        assertTrue(cart.getItems().isEmpty());
    }

}
