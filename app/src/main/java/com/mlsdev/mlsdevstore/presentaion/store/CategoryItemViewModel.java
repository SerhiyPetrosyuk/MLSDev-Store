package com.mlsdev.mlsdevstore.presentaion.store;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.mlsdev.mlsdevstore.data.model.category.CategoryTreeNode;
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseItemViewModel;

public class CategoryItemViewModel extends BaseItemViewModel<CategoryTreeNode> {
    private CategoryTreeNode categoryTreeNode;
    public ObservableField<String> categoryName = new ObservableField<>();
    public ObservableBoolean leafNode = new ObservableBoolean();

    public CategoryItemViewModel(CategoryTreeNode categoryTreeNode) {
        this.categoryTreeNode = categoryTreeNode;
    }

    public CategoryItemViewModel() {
    }

    @Override
    public void setData(CategoryTreeNode data) {
        categoryTreeNode = data;
        categoryName.set(categoryTreeNode.getCategory().getCategoryName());
        leafNode.set(categoryTreeNode.isLeafCategoryTreeNode());
        leafNode.notifyChange();
    }
}
