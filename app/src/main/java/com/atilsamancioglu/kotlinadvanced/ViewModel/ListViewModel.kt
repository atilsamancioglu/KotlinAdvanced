package com.atilsamancioglu.kotlinadvanced.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.atilsamancioglu.kotlinadvanced.Util.NotificationsHelper
import com.atilsamancioglu.kotlinadvanced.model.Country
import com.atilsamancioglu.kotlinadvanced.model.DogDatabase
import com.atilsamancioglu.kotlinadvanced.model.DogsAPIService
import com.atilsamancioglu.kotlinadvanced.Util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application):BaseViewModel(application) {

    private val dogsService = DogsAPIService()
    private val disposable = CompositeDisposable() //observe the observables that API gives us and not having to worry about getting rid of it, no memory leaks.

    //this will actually provide data from our data source
    // it can be api, local database, firebase etc.


    private val prefHelper = SharedPreferencesHelper(getApplication())

    private val refreshTime = 5 * 60 * 1000 * 1000 * 1000L //nano seconds

    val dogs = MutableLiveData<List<Country>>()

    //will notify there is an error to whoever is listening this data
    val dogsLoadError = MutableLiveData<Boolean>()

    //will tell whoever is listening that the data is listening actually
    //view will decide what to display, in our case it will display the spinner
    val loading = MutableLiveData<Boolean>()

    fun refresh() {

        val updateTime = prefHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        } else {
            fetchFromRemote()
        }


        /*
        val dog1 = DogBreed("1","Corgi","15","Group","For","Temperament","imageUrl")
        val dog2 = DogBreed("2","Labrador","10","Group","For","Temperament","imageUrl")
        val dog3 = DogBreed("3","Rotweiler","20","Group","For","Temperament","imageUrl")
        val dogList = arrayListOf<DogBreed>(dog1,dog2,dog3)
        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false

         */


    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val dogs = DogDatabase(getApplication()).dogDao().getAllDogs()
            dogsRetrieved(dogs)
            Toast.makeText(getApplication(),"Dogs Retreived FROM SQL",Toast.LENGTH_LONG).show()
        }
    }

    fun refreshBypassCache() {
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(dogsService.getDogs()
            .subscribeOn(Schedulers.newThread()) //this should be run in background thread in order not to block it
            .observeOn(AndroidSchedulers.mainThread()) //however this should be processed in main thread, user will se this
            .subscribeWith(object:DisposableSingleObserver<List<Country>>() { //we get data with this, subscribeWith asks for observer which we get from DisposableSingleObserver
                override fun onSuccess(t: List<Country>) {
                    storeDogsLocally(t)
                    Toast.makeText(getApplication(),"Dogs Retreived FROM API",Toast.LENGTH_LONG).show()
                    NotificationsHelper(getApplication()).createNotification()


                }

                override fun onError(e: Throwable) {
                    dogsLoadError.value = true
                    loading.value = false
                    e.printStackTrace()
                }

            })
        )
    }

    private fun dogsRetrieved(dogList: List<Country>) {
        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }

    private fun storeDogsLocally(list: List<Country>) {
        //database cannot be accessed from main thread in order not to block the ui
        //accessing database should be from a background thread

        //this will run this piece of code as a coroutine in a seperate thread
        launch {
            val dao = DogDatabase(getApplication()).dogDao()
            dao.deleteAllDogs()
            val result = dao.insertAll(*list.toTypedArray()) //kotlin trick to expand a list into individiual elements
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                i = i + 1
            }

            dogsRetrieved(list)


        }

        prefHelper.saveUpdateTime(System.nanoTime())

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear() //clean up everything we need
    }


}