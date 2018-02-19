package com.mlsdev.mlsdevstore.presentaion.store;

import android.arch.lifecycle.LifecycleObserver;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.data.cart.Cart;
import com.mlsdev.mlsdevstore.data.model.item.ListItem;
import com.mlsdev.mlsdevstore.data.model.item.SearchResult;
import com.mlsdev.mlsdevstore.databinding.ItemProductBinding;
import com.mlsdev.mlsdevstore.presentaion.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter
        extends RecyclerView.Adapter<BaseViewHolder<ListItem>>
        implements LifecycleObserver {
    protected Cart cart;
    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_FOOTER = 1;
    public static final int VIEW_TYPE_ITEM = 2;
    public static final int HEADER_OR_FOOTER = 1;
    protected boolean withHeader = false;
    protected boolean withFooter = false;
    protected final List<ListItem> items;

    public ProductsAdapter() {
        items = new ArrayList<>();
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public BaseViewHolder<ListItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.item_product,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<ListItem> holder, int position) {
        holder.bindView(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (withHeader && position == 0)
            return VIEW_TYPE_HEADER;
        else if (withFooter && (position == (items.size() - HEADER_OR_FOOTER)))
            return VIEW_TYPE_FOOTER;
        else
            return VIEW_TYPE_ITEM;
    }

    public class ProductViewHolder extends BaseViewHolder<ListItem> {
        private ItemProductBinding binding;

        public ProductViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bindView(ListItem item) {
            if (binding.getViewModel() == null)
                binding.setViewModel(new ProductItemViewModel());

            binding.getViewModel().setItem(cart, item);
        }
    }

    public void setData(SearchResult searchResult) {
        items.clear();
        items.addAll(searchResult.getItemSummaries());
    }

    public void setOnClickListeners(View.OnClickListener... listeners) {

    }

}
