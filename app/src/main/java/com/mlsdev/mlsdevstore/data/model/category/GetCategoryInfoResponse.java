package com.mlsdev.mlsdevstore.data.model.category;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class GetCategoryInfoResponse {

    @Attribute(name = "xmlns", required = false)
    private String xmlns;

    @Element(name = "Timestamp")
    private String timestamp;

    @Element(name = "Build")
    private String buld;

    @Element(name = "Version")
    private int version;

    @Element(name = "Ack")
    private String ack;

    @Element(name = "CategoryArray")
    private CategoryArray categoryArray;

    @Element(name = "CategoryCount")
    private int categoryCount;

    @Element(name = "UpdateTime")
    private String updateTime;

    @Element(name = "CategoryVersion")
    private int categoryVersion;

    public String getTimestamp() {
        return timestamp;
    }

    public String getBuld() {
        return buld;
    }

    public int getVersion() {
        return version;
    }

    public String getAck() {
        return ack;
    }

    public CategoryArray getCategoryArray() {
        return categoryArray;
    }

    public int getCategoryCount() {
        return categoryCount;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public int getCategoryVersion() {
        return categoryVersion;
    }
}
