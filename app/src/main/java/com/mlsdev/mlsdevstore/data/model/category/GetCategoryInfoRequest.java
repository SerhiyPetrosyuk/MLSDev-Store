package com.mlsdev.mlsdevstore.data.model.category;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "GetCategoryInfoRequest", strict = false)
public class GetCategoryInfoRequest {
    public static final String INCLUDE_SELECTOR = "ChildCategories";

    @Element(name = "CategoryID")
    private int categoryID;

    @Element(name = "IncludeSelector")
    private String includeSelector;

    public GetCategoryInfoRequest(int categoryID, String includeSelector) {
        this.categoryID = categoryID;
        this.includeSelector = includeSelector;
    }
}
