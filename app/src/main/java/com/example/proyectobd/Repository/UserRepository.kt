package com.example.proyectobd.Repository

import com.example.proyectobd.DAO.UsuarioDao
import com.example.proyectobd.Model.User

class UserRepository(private val userDao: UsuarioDao) {
    suspend fun insertar(user: User){
        userDao.insert(user)
    }

    suspend fun getAllUsers(): List<User>{
        return userDao.getAllUssers()
    }
    suspend fun deleteById(userId: Int): Int{
        return userDao.deleteById(userId)
    }

    suspend fun updateUser(userId: Int, nombre: String, apellido: String, edad: Int): Int{
        return userDao.updateUser(userId, nombre, apellido, edad)
    }
}