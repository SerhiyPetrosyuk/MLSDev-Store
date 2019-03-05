package com.mlsdev.mlsdevstore.data.cart;

import com.mlsdev.mlsdevstore.data.model.product.Product;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

public class CartTest {

    private Cart cart;
    private Product item;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        cart = new Cart();
//        item = createItem();
    }

//    @Test
//    public void addItem() {
//        cart.addItem(item);
//        assertEquals(1, cart.getItems().size());
//        assertEquals(item.getId(), cart.items.get(0).getId());
//        assertEquals(item.getItemTitle(), cart.items.get(0).getTitle());
//        assertEquals(item.getItemCondition(), cart.items.get(0).getCondition());
//        assertEquals(item.getItemPrice().getCurrency(), Objects.requireNonNull(cart.items.get(0).getPrice()).getCurrency());
//        assertEquals(item.getItemPrice().getValue(), Objects.requireNonNull(cart.items.get(0).getPrice()).getValue(), 0);
//    }
//
//    @Test
//    public void addItem_MaxItemCountWasReached() {
//        cart.addItem(createItem());
//        cart.addItem(createItem());
//        cart.addItem(createItem());
//        cart.addItem(createItem());
//        cart.addItem(createItem());
//
//        assertEquals(4, cart.items.size());
//    }
//
//    @Test
//    public void removeItem() {
//        cart.addItem(item);
//        assertFalse(cart.items.isEmpty());
//
//        cart.removeItem(item.getId());
//        assertTrue(cart.items.isEmpty());
//    }
//
//    private Product createItem() {
//        Price price = new Price();
//        price.setCurrency("USD");
//        price.setValue(Math.random() * 1000);
//
//        Product item = new Product();
//        item.setItemId(String.valueOf(item.hashCode()));
//        item.setTitle("title");
//        item.setCondition(Condition.Companion.getNew());
//        item.setPrice(price);
//
//        return item;
//    }
}
