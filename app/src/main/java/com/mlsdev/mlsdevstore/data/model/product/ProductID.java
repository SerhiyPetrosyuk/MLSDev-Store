package com.mlsdev.mlsdevstore.data.model.product;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

public class ProductID {
    @Attribute
    private String name;
    @Text
    private String id;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
