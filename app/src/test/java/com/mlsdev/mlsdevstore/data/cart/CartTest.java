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

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        cart = new Cart();
        item = createItem();
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
    public void addItem_MaxItemCountWasReached() {
        cart.addItem(createItem());
        cart.addItem(createItem());
        cart.addItem(createItem());
        cart.addItem(createItem());
        cart.addItem(createItem());

        assertEquals(4, cart.getItems().size());
    }

    @Test
    public void removeItem() {
        cart.addItem(item);
        assertFalse(cart.getItems().isEmpty());

        cart.removeItem(item.getId());
        assertTrue(cart.getItems().isEmpty());
    }

    private Item createItem() {
        Price price = new Price();
        price.setCurrency("USD");
        price.setValue(Math.random() * 1000);

        Item item = new Item();
        item.setItemId(String.valueOf(item.hashCode()));
        item.setTitle("title");
        item.setCondition(Item.Condition.New);
        item.setPrice(price);

        return item;
    }
}
