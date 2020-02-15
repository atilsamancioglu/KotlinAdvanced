package com.atilsamancioglu.kotlinadvanced.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity //normalde buraya parantez açıp table name verebilirsin ama vermezsen aşağıdaki sınıf ismi yeterli
data class Country (
    @ColumnInfo(name = "name") //aşağıdaki değişken bu column name ile verilecek
    @SerializedName("name")
    val countryName: String?,
    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    val countryCapital: String?,
    @ColumnInfo(name = "region")
    @SerializedName("region")
    val countryRegion: String?,
    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    val countryCurrency: String?,
    @ColumnInfo(name = "language")
    @SerializedName("language")
    val countryLanguage: String?,
    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    val imageUrl: String?
) {
    //data class doesn't need a body necessarily
    //however we create this for primary key
   @PrimaryKey(autoGenerate = true)
   var uuid: Int = 0
}


data class CountryPalette(var color: Int) {

}