package com.mlsdev.mlsdevstore.presentaion.store;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.mlsdevstore.R;
import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;
import com.mlsdev.mlsdevstore.databinding.ItemCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private List<CategoryTreeNode> categoryTreeNodes = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_category,
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBindView();
    }

    @Override
    public int getItemCount() {
        return categoryTreeNodes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCategoryBinding binding;

        public ViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBindView() {
            CategoryTreeNode node = categoryTreeNodes.get(getAdapterPosition());

            if (binding.getViewModel() == null)
                binding.setViewModel(new CategoryItemViewModel());

            binding.getViewModel().setData(node);
        }
    }

    public void setData(List<CategoryTreeNode> categoryTreeNodes) {
        this.categoryTreeNodes.clear();
        this.categoryTreeNodes.addAll(categoryTreeNodes);
    }

}
