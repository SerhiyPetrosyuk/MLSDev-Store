package com.mlsdev.mlsdevstore.presentaion.cart;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.data.cart.Cart;
import com.mlsdev.mlsdevstore.data.model.item.ListItem;
import com.mlsdev.mlsdevstore.databinding.ItemCartProductBinding;
import com.mlsdev.mlsdevstore.presentaion.adapter.BaseViewHolder;
import com.mlsdev.mlsdevstore.presentaion.store.ProductItemViewModel;
import com.mlsdev.mlsdevstore.presentaion.store.ProductsAdapter;

import javax.inject.Inject;

public class ItemsAdapter extends ProductsAdapter implements
        LifecycleObserver,
        Cart.OnItemCountChangeListener {

    private Cart cart;

    @Inject
    public ItemsAdapter(Cart cart) {
        this.cart = cart;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        cart.addOnItemCountChangeListener(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        cart.removeOnItemCountChangeListener(this);
    }

    @Override
    public BaseViewHolder<ListItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.item_cart_product,
                        parent,
                        false));
    }

    @Override
    public void onItemCountChanged(int count) {
        items.clear();
        items.addAll(cart.getItems());
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends BaseViewHolder<ListItem> {
        private ItemCartProductBinding binding;

        public ProductViewHolder(ItemCartProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bindView(ListItem item) {
            if (binding.getViewModel() == null)
                binding.setViewModel(new ProductItemViewModel());

            if (binding.getViewModel() != null)
                binding.getViewModel().setItem(item);
        }
    }

}
