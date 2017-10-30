package com.mlsdev.mlsdevstore.data.model.category;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class CategoryArray {

    @ElementList(entry = "Category", type = Category.class, inline = true)
    private List<Category> categoryList;

    public List<Category> getCategoryList() {
        return categoryList;
    }
}
