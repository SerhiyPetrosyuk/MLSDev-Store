package com.mlsdev.mlsdevstore.data.model.category;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.mlsdev.mlsdevstore.data.local.database.Column;
import com.mlsdev.mlsdevstore.data.local.database.Table;

@Entity(tableName = Table.CATEGORIES,
        indices = {
                @Index(Column.CATEGORY_ID),
                @Index(Column.CATEGORY_TREE_NODE_ID_FOREIGN_KEY)},
        foreignKeys = @ForeignKey(
                entity = CategoryTreeNode.class,
                parentColumns = Column.CATEGORY_TREE_NODE_ID,
                childColumns = Column.CATEGORY_TREE_NODE_ID_FOREIGN_KEY,
                onDelete = ForeignKey.CASCADE))
public class Category {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = Column.CATEGORY_ID)
    private String categoryId = "default";

    @ColumnInfo(name = Column.CATEGORY_NAME)
    private String categoryName;

    @ColumnInfo(name = Column.CATEGORY_TREE_NODE_ID_FOREIGN_KEY)
    private String categoryTreeNodeId;

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryId(@NonNull String categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryTreeNodeId() {
        return categoryTreeNodeId;
    }

    public void setCategoryTreeNodeId(String categoryTreeNodeId) {
        this.categoryTreeNodeId = categoryTreeNodeId;
    }
}
