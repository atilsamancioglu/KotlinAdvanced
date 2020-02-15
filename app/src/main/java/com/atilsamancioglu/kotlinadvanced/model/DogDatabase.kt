package com.atilsamancioglu.kotlinadvanced.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Country::class),version = 1)
abstract class DogDatabase: RoomDatabase() {

    //you can have a situation where you reach db from different locations with diff. threads
    //all trying to change the data at the same time. we do not want that obviously.
    //if we make DogDatabase into a Singleton class this will resolve our problem since its only instance

    abstract fun dogDao() : CountryDao

    companion object {
        //this creates static variables that can be reached outside of the scope of this class
       @Volatile private var instance : DogDatabase? = null
        // Marks the JVM backing field of the annotated property as volatile, meaning that writes to this field are immediately made visible to other threads.
        //this is the unique instance of this class
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) { //syncronized means if multiple threads are trying to come here, only one will access it
            // ?: is elvis operator, if it is null what happens
            instance ?: buildDatabase(context).also {
                instance = it //we buildDatabase and also assign it to the instance and then return it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, DogDatabase::class.java,"dogdatabase"
        ).build() //we use applicationContext here rather than context because context can be null at some points like rotations
    }
}