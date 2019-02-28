package com.mlsdev.mlsdevstore.data.model.item

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mlsdev.mlsdevstore.data.local.database.tables.ImagesTable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = ImagesTable.NAME)
data class Image(
        @ColumnInfo(name = ImagesTable.COLUMN_URL)
        val imageUrl: String?
) : Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ImagesTable.COLUMN_ID)
    var id: Long = 0

}
