package com.mlsdev.mlsdevstore.data.cart;


import com.mlsdev.mlsdevstore.data.model.item.Item;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Cart {
    private final List<Item> items;
    private final List<OnItemCountChangeListener> itemCountChangeListeners;

    @Inject
    public Cart() {
        items = new ArrayList<>();
        itemCountChangeListeners = new ArrayList<>();
    }

    public void addItem(Item item) {
        for (Item itemFromCart : items)
            if (itemFromCart.getId().equals(item.getId()))
                return;

        items.add(item);
    }

    public void removeItem(String itemId) {
        for (Item item : items)
            if (item.getId().equals(itemId)) {
                items.remove(item);
                break;
            }
    }

    public void addOnItemCountChangeListener(OnItemCountChangeListener listener) {
        itemCountChangeListeners.add(listener);
        listener.onItemCountChanged(items.size());
    }

    public void removeOnItemCountChangeListener(OnItemCountChangeListener listener) {
        itemCountChangeListeners.remove(listener);
    }

    public interface OnItemCountChangeListener {
        void onItemCountChanged(int count);
    }
}
