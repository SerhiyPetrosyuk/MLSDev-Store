package com.mlsdev.mlsdevstore.data.model.product;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class NameValueList {

    @Element(name = "Name", required = false)
    private String name;

    @Element(name = "Value", required = false)
    private String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
