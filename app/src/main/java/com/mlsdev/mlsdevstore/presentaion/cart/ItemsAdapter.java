package com.mlsdev.mlsdevstore.presentaion.cart;

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

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import static com.mlsdev.mlsdevstore.presentaion.store.ProductsAdapterKt.HEADER_OR_FOOTER;
import static com.mlsdev.mlsdevstore.presentaion.store.ProductsAdapterKt.VIEW_TYPE_FOOTER;
import static com.mlsdev.mlsdevstore.presentaion.store.ProductsAdapterKt.VIEW_TYPE_ITEM;

public class ItemsAdapter extends ProductsAdapter
        implements
        Cart.OnItemCountChangeListener,
        Cart.OnItemRemovedListener {

    public ItemsAdapter() {
        super();
        setWithFooter(true);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        getCart().addOnItemCountChangeListener(this);
        getCart().addOnItemRemovedListener(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        getCart().removeOnItemCountChangeListener(this);
        getCart().removeOnItemRemovedListener(this);
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
        if (position < getItems().size())
            super.onBindViewHolder(holder, position);
        else
            holder.bindView(null);
    }

    @Override
    public int getItemCount() {
        if (getItems().isEmpty())
            return super.getItemCount();
        else
            return getItems().size() + HEADER_OR_FOOTER;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItems().size())
            return VIEW_TYPE_FOOTER;
        else
            return VIEW_TYPE_ITEM;
    }

    @Override
    public void onItemCountChanged(int count) {
        if (!getItems().isEmpty())
            return;

        getItems().clear();
        getItems().addAll(getCart().getItems());
        notifyDataSetChanged();
    }

    @Override
    public void onItemRemoved(String itemId) {
        int removedItemPosition = -1;

        for (ListItem item : getItems())
            if (item.getId().equals(itemId))
                removedItemPosition = getItems().indexOf(item);

        getItems().remove(removedItemPosition);
        notifyItemRemoved(removedItemPosition);
        notifyItemChanged(getItems().size());
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
                binding.getViewModel().setItem(getCart(), item);
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
                binding.setViewModel(new TotalSumItemViewModel(getCart().getTotalSum()));
            else
                binding.getViewModel().setTotalSum(getCart().getTotalSum());
        }
    }

}
