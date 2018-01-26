package com.mlsdev.mlsdevstore.presentaion.store;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.data.model.item.ListItem;
import com.mlsdev.mlsdevstore.data.model.item.SearchResult;
import com.mlsdev.mlsdevstore.databinding.ItemProductBinding;
import com.mlsdev.mlsdevstore.presentaion.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<BaseViewHolder<ListItem>> {
    static final int VIEW_TYPE_HEADER = 0;
    static final int VIEW_TYPE_FOOTER = 1;
    static final int VIEW_TYPE_ITEM = 2;
    static final int HEADER_OR_FOOTER = 1;
    final List<ListItem> items;

    public ProductsAdapter() {
        items = new ArrayList<>();
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

            binding.getViewModel().setItem(item);
        }
    }

    public void setData(SearchResult searchResult) {
        items.clear();
        items.addAll(searchResult.getItemSummaries());
    }

    public void setOnClickListeners(View.OnClickListener... listeners){

    }

}
