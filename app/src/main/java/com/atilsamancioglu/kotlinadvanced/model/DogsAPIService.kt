package com.atilsamancioglu.kotlinadvanced.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DogsAPIService {

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) //this will conver json into gson (our model class representation)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //this will convert our list of objects into single, an observable
        .build()
        .create(DogsAPI::class.java)

    fun getDogs(): Single<List<Country>> {
        return api.getDogs()
    }

    //single is an observable that emits data once and then finishes
}