package com.mlsdev.mlsdevstore.data.model.category;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "GetCategoryInfoRequest", strict = false)
public class GetCategoryInfoRequest {
    private static final String INCLUDE_SELECTOR = "ChildCategories";
    private static final int ROOT_CATEGORY_ID = -1;

    @Element(name = "CategoryID")
    private int categoryID;

    @Element(name = "IncludeSelector")
    private String includeSelector;

    public GetCategoryInfoRequest(int categoryID, String includeSelector) {
        this.categoryID = categoryID;
        this.includeSelector = includeSelector;
    }

    public GetCategoryInfoRequest() {
        categoryID = ROOT_CATEGORY_ID;
        includeSelector = INCLUDE_SELECTOR;
    }
}
