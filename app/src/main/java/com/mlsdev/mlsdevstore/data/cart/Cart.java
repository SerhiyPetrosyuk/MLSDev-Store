package com.mlsdev.mlsdevstore.data.cart;


import com.mlsdev.mlsdevstore.data.model.item.Item;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Cart {
    public final static int MAX_ITEMS_PER_CHECKOUT = 4;
    private final List<Item> items;
    private final List<OnItemCountChangeListener> itemCountChangeListeners;
    private final List<OnItemRemovedListener> itemRemovedListeners;
    private final List<OnMaxItemsReachedListener> maxItemsReachedListeners;
    private final List<OnItemAddedListener> itemAddedListeners;

    @Inject
    public Cart() {
        items = new ArrayList<>();
        itemCountChangeListeners = new ArrayList<>();
        itemRemovedListeners = new ArrayList<>();
        itemAddedListeners = new ArrayList<>();
        maxItemsReachedListeners = new ArrayList<>();
    }

    public void addItem(Item item) {
        if (item == null) {
            return;
        } else if (items.size() == MAX_ITEMS_PER_CHECKOUT) {
            notifyOnMaxItemsReached();
            return;
        }

        for (Item itemFromCart : items)
            if (itemFromCart.getId().equals(item.getId()))
                return;

        items.add(item);
        notifyItemCountChanged();
        notifyOnItemAdded(item);
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

    private void notifyOnMaxItemsReached() {
        for (OnMaxItemsReachedListener listener : maxItemsReachedListeners)
            listener.onMaxItemsReached();
    }

    private void notifyOnItemAdded(Item item) {
        for (OnItemAddedListener listener : itemAddedListeners)
            listener.onItemAdded(item);
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

    public void addOnMaxItemsReachedListener(OnMaxItemsReachedListener listener) {
        maxItemsReachedListeners.add(listener);
    }

    public void removeOnMaxItemsReachedListener(OnMaxItemsReachedListener listener) {
        maxItemsReachedListeners.remove(listener);
    }

    public void addOnItemAddedListener(OnItemAddedListener listener) {
        itemAddedListeners.add(listener);
    }

    public void removeOnItemAddedListener(OnItemAddedListener listener) {
        itemAddedListeners.remove(listener);
    }

    public interface OnItemCountChangeListener {
        void onItemCountChanged(int count);
    }

    public interface OnItemRemovedListener {
        void onItemRemoved(String itemId);
    }

    public interface OnMaxItemsReachedListener {
        void onMaxItemsReached();
    }

    public interface OnItemAddedListener {
        void onItemAdded(Item item);
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

    public void reset() {
        items.clear();
        notifyItemCountChanged();
    }
}
