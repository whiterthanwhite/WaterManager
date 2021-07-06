package com.bst.watermanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Container(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "container_name") var name: String?,
    @ColumnInfo(name = "container_volume") var volume: Int?
)