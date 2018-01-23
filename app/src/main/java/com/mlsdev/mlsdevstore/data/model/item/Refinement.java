package com.mlsdev.mlsdevstore.data.model.item;

import java.util.ArrayList;
import java.util.List;

public class Refinement {

    private List<CategoryDistribution> categoryDistributions = new ArrayList<>();

    public List<CategoryDistribution> getCategoryDistributions() {
        return categoryDistributions;
    }

    public void setCategoryDistributions(List<CategoryDistribution> categoryDistributions) {
        this.categoryDistributions = categoryDistributions;
    }
}
