package com.atilsamancioglu.kotlinadvanced.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    //difference between androidviewmodel and viewmodel is the presence of application context
    //we will access the database and database will ask for a context
    //we want application context for database rather than activity, fragment etc.

    //CourutineScope allows us to have corouitines in this model

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    // we are going to have a job and when it's finished we're going to go back to main thread

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}