package com.example.proyectobd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Dao
import com.example.proyectobd.DAO.UsuarioDao
import com.example.proyectobd.Database.UserDatabase
import com.example.proyectobd.Repository.UserRepository
import com.example.proyectobd.Screen.UserApp
import com.example.proyectobd.ui.theme.ProyectoBDTheme

class MainActivity : ComponentActivity() {

    private lateinit var userDao: UsuarioDao
    private lateinit var userRepository: UserRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db= UserDatabase.getDatabase(applicationContext)
        userDao = db.userDao()
        userRepository = UserRepository(userDao)

        enableEdgeToEdge()
        setContent {
            UserApp(userRepository)
        }
    }
}
