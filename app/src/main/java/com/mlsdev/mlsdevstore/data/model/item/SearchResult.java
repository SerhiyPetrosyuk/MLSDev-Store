package com.mlsdev.mlsdevstore.data.model.item;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

    private int limit;
    private int offset;
    private int total;
    private String next;
    private String prev;
    private List<Item> itemSummaries;
    private Refinement refinement;

    public SearchResult() {
        itemSummaries = new ArrayList<>();
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getTotal() {
        return total;
    }

    public String getNext() {
        return next;
    }

    public String getPrev() {
        return prev;
    }

    public List<Item> getItemSummaries() {
        return itemSummaries;
    }

    public Refinement getRefinement() {
        return refinement;
    }

    public void setRefinement(Refinement refinement) {
        this.refinement = refinement;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
