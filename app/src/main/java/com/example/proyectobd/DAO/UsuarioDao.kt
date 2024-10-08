package com.example.proyectobd.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.proyectobd.Model.User

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM users")
    suspend fun getAllUssers(): List<User>

    @Query ("DELETE FROM users WHERE id = :userId")
    suspend fun deleteById(userId: Int): Int

    @Query ("UPDATE users SET nombre = :nombre, apellido = :apellido, edad = :edad WHERE id = :userId")
    suspend fun updateUser(userId: Int, nombre: String, apellido: String, edad: Int): Int
}