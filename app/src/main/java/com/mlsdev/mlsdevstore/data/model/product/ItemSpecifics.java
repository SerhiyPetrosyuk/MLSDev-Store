package com.mlsdev.mlsdevstore.data.model.product;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class ItemSpecifics {

    @ElementList(entry = "NameValueList", type = NameValueList.class, required = false, inline = true)
    private List<NameValueList> namesAndValues;

    public List<NameValueList> getNamesAndValues() {
        return namesAndValues;
    }
}
