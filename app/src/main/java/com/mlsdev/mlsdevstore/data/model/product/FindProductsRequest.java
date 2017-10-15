package com.mlsdev.mlsdevstore.data.model.product;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "FindProductsRequest", strict = false)
public class FindProductsRequest {

    @Attribute
    private String xmlns = "urn:ebay:apis:eBLBaseComponents";

    @Element(name = "QueryKeywords")
    private String queryKeywords;

    @Element(name = "MaxEntries")
    private int maxEntries;

    @Element(name = "AvailableItemsOnly")
    private boolean availableItemsOnly;

    public FindProductsRequest(String queryKeywords, int maxEntries, boolean availableItemsOnly) {
        this.queryKeywords = queryKeywords;
        this.maxEntries = maxEntries;
        this.availableItemsOnly = availableItemsOnly;
    }
}
