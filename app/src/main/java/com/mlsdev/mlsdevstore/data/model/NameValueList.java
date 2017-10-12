package com.mlsdev.mlsdevstore.data.model;

import org.simpleframework.xml.Element;

public class NameValueList {

    @Element(name = "Name", required = false)
    private String name;

    @Element(name = "Value", required = false)
    private String value;

}
