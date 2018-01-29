package com.mlsdev.mlsdevstore.presentaion.store;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.data.model.item.Item;
import com.mlsdev.mlsdevstore.data.model.item.ListItem;
import com.mlsdev.mlsdevstore.data.model.item.SearchResult;
import com.mlsdev.mlsdevstore.databinding.ItemFooterCategoryBinding;
import com.mlsdev.mlsdevstore.databinding.ItemHeaderCategoryBinding;
import com.mlsdev.mlsdevstore.presentaion.adapter.BaseViewHolder;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseHeaderViewModel;

public class RandomProductsAdapter extends ProductsAdapter {

    private boolean withHeader = false;
    private boolean withFooter = false;
    private View.OnClickListener onFooterClickListener;
    private View.OnClickListener onAllCategoriesListener;
    private View.OnClickListener onAuthorizeListener;

    public RandomProductsAdapter() {
        super();
    }

    @Override
    public BaseViewHolder<ListItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder<ListItem> viewHolder;

        if (viewType == VIEW_TYPE_HEADER) {
            viewHolder = new CategoryNameHeaderViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.getContext()),
                            R.layout.item_header_category,
                            parent,
                            false));
        } else if (viewType == VIEW_TYPE_FOOTER) {
            viewHolder = new ReadMoreInCategoryFooter(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.getContext()),
                            R.layout.item_footer_category,
                            parent,
                            false));
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (withHeader && position == 0)
            return VIEW_TYPE_HEADER;
        else if (withFooter && (position == (items.size() - HEADER_OR_FOOTER)))
            return VIEW_TYPE_FOOTER;
        else
            return super.getItemViewType(position);
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
            binding.btnAllCategories.setOnClickListener(onAllCategoriesListener);
            binding.btnSignIn.setOnClickListener(onAuthorizeListener);
            binding.btnSignUp.setOnClickListener(onAuthorizeListener);
        }
    }

    public class ReadMoreInCategoryFooter extends BaseViewHolder<ListItem> {
        private ItemFooterCategoryBinding binding;

        public ReadMoreInCategoryFooter(ItemFooterCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.btnGetMoreItems.setOnClickListener(onFooterClickListener);
        }

        @Override
        public void bindView(ListItem item) {
            if (binding.getViewModel() == null)
                binding.setViewModel(new BaseHeaderViewModel());

            binding.getViewModel().setHeader(item.getTitle());
        }
    }

    @Override
    public void setData(SearchResult searchResult) {
        int startPosition = searchResult.getItemSummaries().size() == items.size() && searchResult.getOffset() > 0 ? getItemCount() : 0;
        items.clear();
        items.addAll(searchResult.getItemSummaries());

        if (searchResult.getRefinement() != null
                && !searchResult.getRefinement().getCategoryDistributions().isEmpty()
                && !items.isEmpty()) {
            String title = searchResult.getRefinement().getCategoryDistributions().get(0).getCategoryName();
            ListItem headerAndFooter = new Item(title);
            items.add(0, headerAndFooter);
            items.add(headerAndFooter);
            withHeader = true;
            withFooter = true;
        }

        notifyItemRangeChanged(startPosition, getItemCount());
    }

    @Override
    public void setOnClickListeners(View.OnClickListener... listeners) {

        if (listeners.length > 0)
            onAuthorizeListener = listeners[0];

        if (listeners.length > 1)
            onAllCategoriesListener = listeners[1];

        if (listeners.length > 2)
            onFooterClickListener = listeners[2];

    }
}