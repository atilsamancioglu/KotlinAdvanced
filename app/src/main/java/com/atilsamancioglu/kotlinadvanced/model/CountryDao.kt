package com.atilsamancioglu.kotlinadvanced.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

//in retrofit we had one interface one service to manage operations. in room we do the same thing actually

@Dao //data access object, interface through which we will access the database
interface CountryDao {

    @Insert
    suspend fun insertAll(vararg dogs: Country) : List<Long>

    //vararg -> variable, multiple arguments of DogBreed. by using this we can spesify as many dogbreed objects into this funciton as we want.
    // List<Long> -> this will return list of longs and the longs that we are looking for is the primary IDs
    //suspend -> this function will be done on a seperate thread, on background
    /*
    Suspending functions are at the center of everything coroutines.
    A suspending function is simply a function that can be paused and resumed at a later time.
    They can execute a long running operation and wait for it to complete without blocking.

    The syntax of a suspending function is similar to that of a regular function except for the
    addition of the suspend keyword. It can take a parameter and have a return type.
    However, suspending functions can only be invoked by another suspending
    function or within a coroutine.
     */

    @Query("SELECT * FROM country") //table name is dogbreed bcs we didn't change data class name
    suspend fun getAllDogs() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :dogId")
    suspend fun getDog(dogId : Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllDogs()


}