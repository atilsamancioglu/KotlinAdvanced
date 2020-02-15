package com.atilsamancioglu.kotlinadvanced.ViewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.atilsamancioglu.kotlinadvanced.model.Country
import com.atilsamancioglu.kotlinadvanced.model.DogDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): BaseViewModel(application) {

    //MVVM recommends to have different view models for different views. we could put this in listviewmmodel as well but we don't do that.
    val dogLiveData = MutableLiveData<Country>()

    fun fetch(uuid: Int) {


        launch {
            val dog = DogDatabase(getApplication()).dogDao().getDog(uuid)
            dogLiveData.value = dog
        }


        /*
        val dog = DogBreed("1","Corgi","15","Group","For","Temperament","imageUrl")
        dogLiveData.value = dog

         */
    }
}