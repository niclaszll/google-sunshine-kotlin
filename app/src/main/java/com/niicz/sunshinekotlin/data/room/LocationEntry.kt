package com.niicz.sunshinekotlin.data.room

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = LocationEntry.TABLE_NAME)
class LocationEntry {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_LOCATION_KEY)
    var lKey: Long? = null //foreign key not null is not working

    @SerializedName("id")
    @ColumnInfo(name = COLUMN_lID)
    var lID: Long = 0

    @SerializedName("name")
    @ColumnInfo(name = COLUMN_CITY_NAME)
    var cityName: String = ""

    @SerializedName("coord")
    @Embedded(prefix = "coord_")
    var coord: Coord? = null

    @SerializedName("country")
    @ColumnInfo(name = COLUMN_COUNTRY)
    var country: String = ""

    class Coord {
        @SerializedName("lat")
        @ColumnInfo(name = COLUMN_COORD_LAT)
        var coordLat: String = ""

        @SerializedName("lon")
        @ColumnInfo(name = COLUMN_COORD_LONG)
        var coordLong: String = ""

    }

    companion object {
        const val TABLE_NAME = "location"
        const val COLUMN_lID = "lID"
        const val COLUMN_LOCATION_KEY = "lKey"
        const val COLUMN_CITY_NAME = "cityName"
        const val COLUMN_COORD_LAT = "coordLat"
        const val COLUMN_COORD_LONG = "coordLong"
        const val COLUMN_COUNTRY = "country"
    }
}

