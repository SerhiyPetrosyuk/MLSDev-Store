package com.mlsdev.mlsdevstore.data.model.category;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.mlsdev.mlsdevstore.data.local.database.Column;
import com.mlsdev.mlsdevstore.data.local.database.Table;
import com.mlsdev.mlsdevstore.data.local.database.converter.CategoryConverter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Field;

@Entity(tableName = Table.CATEGORY_TREE_NODES)
public class CategoryTreeNode {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Column.CATEGORY_TREE_NODE_ID)
    private long id;

    @ColumnInfo(name = Column.CATEGORY)
    @TypeConverters({CategoryConverter.class})
    private Category category;

    @ColumnInfo(name = Column.PARENT_CATEGORY_TREE_NODE_HREF)
    private String parentCategoryTreeNodeHref;

    @Ignore
    private List<CategoryTreeNode> childCategoryTreeNodes;

    @ColumnInfo(name = Column.CATEGORY_TREE_NODE_LEVEL)
    private int categoryTreeNodeLevel;

    @ColumnInfo(name = Column.LEAF_CATEGORY_TREE_NODE)
    private boolean leafCategoryTreeNode;

    //region getters

    public long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getParentCategoryTreeNodeHref() {
        return parentCategoryTreeNodeHref;
    }

    public List<CategoryTreeNode> getChildCategoryTreeNodes() {
        return childCategoryTreeNodes != null ? childCategoryTreeNodes : new ArrayList<>();
    }

    public int getCategoryTreeNodeLevel() {
        return categoryTreeNodeLevel;
    }

    public boolean isLeafCategoryTreeNode() {
        return leafCategoryTreeNode;
    }

    //endregion

    //region setters

    public void setId(long id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setParentCategoryTreeNodeHref(String parentCategoryTreeNodeHref) {
        this.parentCategoryTreeNodeHref = parentCategoryTreeNodeHref;
    }

    public void setChildCategoryTreeNodes(List<CategoryTreeNode> childCategoryTreeNodes) {
        this.childCategoryTreeNodes = childCategoryTreeNodes;
    }

    public void setCategoryTreeNodeLevel(int categoryTreeNodeLevel) {
        this.categoryTreeNodeLevel = categoryTreeNodeLevel;
    }

    public void setLeafCategoryTreeNode(boolean leafCategoryTreeNode) {
        this.leafCategoryTreeNode = leafCategoryTreeNode;
    }

    //endregion
}
