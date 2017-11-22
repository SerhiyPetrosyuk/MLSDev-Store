package com.mlsdev.mlsdevstore.presentaion.store;

import android.databinding.ObservableField;

import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseItemViewModel;

public class CategoryItemViewModel extends BaseItemViewModel<CategoryTreeNode> {
    private CategoryTreeNode categoryTreeNode;
    public ObservableField<String> categoryName = new ObservableField<>();

    public CategoryItemViewModel(CategoryTreeNode categoryTreeNode) {
        this.categoryTreeNode = categoryTreeNode;
    }

    public CategoryItemViewModel() {
    }

    @Override
    public void setData(CategoryTreeNode data) {
        categoryTreeNode = data;
        categoryName.set(categoryTreeNode.getCategory().getCategoryName());
    }
}
