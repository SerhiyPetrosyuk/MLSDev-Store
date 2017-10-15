package com.mlsdev.mlsdevstore.data.model.category;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class GetCategoryInfoResponse {

    @Attribute
    private String xmlns;

    @Element(name = "Timestamp")
    private String timestamp;

    @Element(name = "Build")
    private String buld;

    @Element(name = "Version")
    private int version;

    @Element(name = "CategoryArray")
    private CategoryArray categoryArray;

    public String getTimestamp() {
        return timestamp;
    }

    public String getBuld() {
        return buld;
    }

    public int getVersion() {
        return version;
    }

    public CategoryArray getCategoryArray() {
        return categoryArray;
    }
}
