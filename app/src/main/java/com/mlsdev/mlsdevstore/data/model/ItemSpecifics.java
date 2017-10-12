package com.mlsdev.mlsdevstore.data.model;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class ItemSpecifics {

    @ElementList(name = "NameValueList")
    private List<NameValueList> nameValueList;

}
