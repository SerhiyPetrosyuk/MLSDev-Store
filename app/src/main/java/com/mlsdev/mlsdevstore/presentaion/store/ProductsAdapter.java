package com.mlsdev.mlsdevstore.presentaion.store;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.data.model.item.ListItem;
import com.mlsdev.mlsdevstore.data.model.item.SearchResult;
import com.mlsdev.mlsdevstore.databinding.ItemFooterCategoryBinding;
import com.mlsdev.mlsdevstore.databinding.ItemHeaderCategoryBinding;
import com.mlsdev.mlsdevstore.databinding.ItemProductBinding;
import com.mlsdev.mlsdevstore.presentaion.adapter.BaseViewHolder;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseHeaderViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ProductsAdapter extends RecyclerView.Adapter<BaseViewHolder<ListItem>> {

    private List<ListItem> items;

    @Inject
    public ProductsAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public BaseViewHolder<ListItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<ListItem> holder, int position) {
        holder.bindView(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
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

    public class CategoryNameHeaderViewHolder extends BaseViewHolder<ListItem> {
        private ItemHeaderCategoryBinding binding;

        public CategoryNameHeaderViewHolder(ItemHeaderCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bindView(ListItem item) {
            if (binding.getViewModel() == null)
                binding.setViewModel(new BaseHeaderViewModel());

            binding.getViewModel().setHeader(item.getTitle());
        }
    }

    public class ReadMoreInCategoryFooter extends BaseViewHolder<ListItem> {
        private ItemFooterCategoryBinding binding;

        public ReadMoreInCategoryFooter(ItemFooterCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bindView(ListItem item) {
            if (binding.getViewModel() == null)
                binding.setViewModel(new BaseHeaderViewModel());

            binding.getViewModel().setHeader(item.getTitle());
        }
    }

    public void setData(SearchResult searchResult) {

        items.clear();
        items.addAll(searchResult.getItemSummaries());

        if (searchResult.getRefinement() == null)
            return;
        
        if (!searchResult.getRefinement().getCategoryDistributions().isEmpty() && !items.isEmpty()) {
            String title = searchResult.getRefinement().getCategoryDistributions().get(0).getCategoryName();
            ListItem headerAndFooter = new Item(title);
            items.add(0, headerAndFooter);
        }
    }

}
