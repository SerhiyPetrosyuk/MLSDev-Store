package com.mlsdev.mlsdevstore.data.model.category;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Category {

    @Element(name = "CategoryID")
    private int categoryID;

    @Element(name = "CategoryLevel")
    private int categoryLevel;

    @Element(name = "CategoryName")
    private String categoryName;

    @Element(name = "CategoryParentID")
    private int categoryParentID;

    @Element(name = "CategoryNamePath")
    private String categoryNamePath;

    @Element(name = "CategoryIDPath")
    private String categoryIDPath;

    @Element(name = "LeafCategory")
    private String leafCategory;

    public int getCategoryID() {
        return categoryID;
    }

    public int getCategoryLevel() {
        return categoryLevel;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryParentID() {
        return categoryParentID;
    }

    public String getCategoryNamePath() {
        return categoryNamePath;
    }

    public String getCategoryIDPath() {
        return categoryIDPath;
    }

    public String getLeafCategory() {
        return leafCategory;
    }

}
