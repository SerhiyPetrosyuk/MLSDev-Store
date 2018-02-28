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
    private final List<OnItemRemovedListener> itemRemovedListeners;

    @Inject
    public Cart() {
        items = new ArrayList<>();
        itemCountChangeListeners = new ArrayList<>();
        itemRemovedListeners = new ArrayList<>();
    }

    public void addItem(Item item) {
        if (item == null)
            return;

        for (Item itemFromCart : items)
            if (itemFromCart.getId().equals(item.getId()))
                return;

        items.add(item);
        notifyItemCountChanged();
    }

    public void removeItem(String itemId) {
        if (itemId == null || itemId.length() == 0)
            return;

        for (Item item : items)
            if (item.getId().equals(itemId)) {
                items.remove(item);
                break;
            }

        notifyItemRemoved(itemId);
        notifyItemCountChanged();
    }

    private void notifyItemRemoved(String itemId) {
        for (OnItemRemovedListener listener : itemRemovedListeners)
            listener.onItemRemoved(itemId);
    }

    private void notifyItemCountChanged() {
        for (OnItemCountChangeListener listener : itemCountChangeListeners)
            listener.onItemCountChanged(items.size());
    }

    public void addOnItemCountChangeListener(OnItemCountChangeListener listener) {
        itemCountChangeListeners.add(listener);
        listener.onItemCountChanged(items.size());
    }

    public void removeOnItemCountChangeListener(OnItemCountChangeListener listener) {
        itemCountChangeListeners.remove(listener);
    }

    public void addOnItemRemovedListener(OnItemRemovedListener listener) {
        itemRemovedListeners.add(listener);
    }

    public void removeOnItemRemovedListener(OnItemRemovedListener listener) {
        itemRemovedListeners.remove(listener);
    }

    public interface OnItemCountChangeListener {
        void onItemCountChanged(int count);
    }

    public interface OnItemRemovedListener {
        void onItemRemoved(String itemId);
    }

    public List<Item> getItems() {
        return items;
    }

    public double getTotalSum() {
        double totalSum = 0d;

        for (Item item : items)
            totalSum += item.getPrice().getValue();

        return totalSum;
    }
}
