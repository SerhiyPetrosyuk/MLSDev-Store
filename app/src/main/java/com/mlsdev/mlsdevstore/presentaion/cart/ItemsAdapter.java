package com.mlsdev.mlsdevstore.presentaion.cart;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.data.cart.Cart;
import com.mlsdev.mlsdevstore.data.model.item.ListItem;
import com.mlsdev.mlsdevstore.databinding.ItemCartProductBinding;
import com.mlsdev.mlsdevstore.databinding.ItemOrderTotalBinding;
import com.mlsdev.mlsdevstore.presentaion.adapter.BaseViewHolder;
import com.mlsdev.mlsdevstore.presentaion.store.ProductItemViewModel;
import com.mlsdev.mlsdevstore.presentaion.store.ProductsAdapter;

public class ItemsAdapter extends ProductsAdapter
        implements
        Cart.OnItemCountChangeListener,
        Cart.OnItemRemovedListener {

    public ItemsAdapter() {
        super();
        withFooter = true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        cart.addOnItemCountChangeListener(this);
        cart.addOnItemRemovedListener(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        cart.removeOnItemCountChangeListener(this);
        cart.removeOnItemRemovedListener(this);
    }

    @Override
    public BaseViewHolder<ListItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM)
            return new ProductViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.getContext()),
                            R.layout.item_cart_product,
                            parent,
                            false));
        else
            return new TotalSumViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.getContext()),
                            R.layout.item_order_total,
                            parent,
                            false
                    ));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<ListItem> holder, int position) {
        if (position < items.size())
            super.onBindViewHolder(holder, position);
        else
            holder.bindView(null);
    }

    @Override
    public int getItemCount() {
        if (items.isEmpty())
            return super.getItemCount();
        else
            return items.size() + HEADER_OR_FOOTER;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == items.size())
            return VIEW_TYPE_FOOTER;
        else
            return VIEW_TYPE_ITEM;
    }

    @Override
    public void onItemCountChanged(int count) {
        if (!items.isEmpty())
            return;

        items.clear();
        items.addAll(cart.getItems());
        notifyDataSetChanged();
    }

    @Override
    public void onItemRemoved(String itemId) {
        int removedItemPosition = -1;

        for (ListItem item : items)
            if (item.getId().equals(itemId))
                removedItemPosition = items.indexOf(item);

        items.remove(removedItemPosition);
        notifyItemRemoved(removedItemPosition);
        notifyItemChanged(items.size());
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
                binding.getViewModel().setItem(cart, item);
        }
    }

    public class TotalSumViewHolder extends BaseViewHolder<ListItem> {
        private ItemOrderTotalBinding binding;

        public TotalSumViewHolder(ItemOrderTotalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bindView(ListItem item) {
            if (binding.getViewModel() == null)
                binding.setViewModel(new TotalSumItemViewModel(cart.getTotalSum()));
            else
                binding.getViewModel().setTotalSum(cart.getTotalSum());
        }
    }

}
