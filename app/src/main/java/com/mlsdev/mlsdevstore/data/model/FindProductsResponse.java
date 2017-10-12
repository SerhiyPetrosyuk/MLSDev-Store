package com.mlsdev.mlsdevstore.data.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class FindProductsResponse {

    @Attribute(name = "xmlns", required = false)
    private String xmlns;

    @Element(name = "Timestamp")
    private String timestamp;

    @Element(name = "Build")
    private String buld;

    @Element(name = "Version")
    private int version;

    @Element(name = "ApproximatePages")
    private int approximatePages;

    @Element(name = "MoreResults")
    private boolean moreResults;

    @Element(name = "PageNumber")
    private int pageNumber;

    @Element(name = "TotalProducts")
    private int totalProducts;

    @Element(name = "Ack")
    private String ack;

    @ElementList(entry = "Product", type = Product.class, inline = true)
    private List<Product> products;

}
