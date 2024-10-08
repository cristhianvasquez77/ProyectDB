package com.example.proyectobd.Database

import androidx.room.RoomDatabase
import com.example.proyectobd.DAO.UsuarioDao



import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.example.proyectobd.Model.User;

import kotlin.jvm.Volatile;

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
