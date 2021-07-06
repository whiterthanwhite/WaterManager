package com.bst.watermanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class VolumeStatistic (
    @PrimaryKey val date: Date = Date(),
    @ColumnInfo var volume: Int? = null
)