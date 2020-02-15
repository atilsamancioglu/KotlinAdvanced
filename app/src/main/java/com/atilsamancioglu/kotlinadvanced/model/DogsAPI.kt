package com.atilsamancioglu.kotlinadvanced.model

import io.reactivex.Single
import retrofit2.http.GET

interface DogsAPI {

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getDogs():Single<List<Country>>

    //if i had multiple end points i could have listed over here like:
    /*
    @POST("DevTides/DogsApi/master/dogs2.json")
    fun getDogs2():Single<List<DogBreed2>>

     */
}