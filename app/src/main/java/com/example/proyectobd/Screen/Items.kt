package com.example.proyectobd.Screen

import android.widget.Toast
import androidx.benchmark.perfetto.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.room.Dao
import com.example.proyectobd.DAO.UsuarioDao
import com.example.proyectobd.Model.User
import com.example.proyectobd.Repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserApp(userRepository: UserRepository) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var isEditing by remember { mutableStateOf(false) }

    var users by rememberSaveable  { mutableStateOf(listOf<User>()) }
    var selectedUser by remember { mutableStateOf<User?>(null) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text(text = "Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Blue,
                focusedLabelColor = Color.Blue
            )

        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text(text = "Apellido") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Blue,
                focusedLabelColor = Color.Blue
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text(text = "Edad") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Blue,
                focusedLabelColor = Color.Blue
            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        val isFormValid = nombre.isNotBlank() && apellido.isNotBlank() && edad.isNotBlank() && edad.toIntOrNull() != null

        Button(onClick = {
            if (isFormValid) {
                if (isEditing && selectedUser != null) {

                    scope.launch {
                        withContext(Dispatchers.IO) {
                            userRepository.updateUser(
                                userId = selectedUser!!.id,
                                nombre = nombre,
                                apellido = apellido,
                                edad = edad.toIntOrNull() ?: 0
                            )
                            users = userRepository.getAllUsers()
                        }
                        Toast.makeText(context, "Cambios Guardados", Toast.LENGTH_LONG).show()
                        nombre = ""
                        apellido = ""
                        edad = ""
                        selectedUser = null
                        isEditing = false
                    }
                } else {

                    val user = User(
                        nombre = nombre,
                        apellido = apellido,
                        edad = edad.toIntOrNull() ?: 0
                    )
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            userRepository.insertar(user)
                        }
                        Toast.makeText(context, "Usuario Registrado", Toast.LENGTH_LONG).show()
                        nombre = ""
                        apellido = ""
                        edad = ""
                    }
                }
            } else {
                Toast.makeText(context, "Por favor complete todos los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = if (isEditing) Icons.Default.Done else Icons.Default.Add,
                contentDescription = if (isEditing) "Guardar Cambios" else "Registrar Usuario"
            )
            Text(text = if (isEditing) "Guardar Cambios" else "Registrar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            scope.launch {
                users = withContext(Dispatchers.IO) {
                    userRepository.getAllUsers()
                }
            }
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green,
                contentColor = Color.Black
            )
        ) {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = "Listar Usuarios"
            )
            Text(text = "Listar")
        }
        Spacer(modifier = Modifier.height(16.dp))

        var showDialog by remember { mutableStateOf(false) }
        var userToDelete by remember { mutableStateOf<User?>(null) }
        Column {
            users.forEach { user ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "${user.nombre} ${user.apellido}, Edad: ${user.edad}")

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(onClick = {
                        selectedUser = user
                        nombre = user.nombre
                        apellido = user.apellido
                        edad = user.edad.toString()
                        isEditing = true
                    }) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Editar este usuario",
                            tint = Color.Blue
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(onClick = {
                        showDialog = true
                        userToDelete = user
                    }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Eliminar usuario",
                            tint = Color.Red
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
            if (showDialog && userToDelete != null) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                        userToDelete = null
                    },
                    title = {
                        Text(text = "Confirmar Eliminación")
                    },
                    text = {
                        Text(text = "¿Estás seguro de que deseas eliminar a ${userToDelete?.nombre}?")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                userToDelete?.let { user ->
                                    scope.launch {
                                        try {
                                            withContext(Dispatchers.IO) {
                                                userRepository.deleteById(user.id)
                                                users = userRepository.getAllUsers()
                                            }
                                            Toast.makeText(context, "Usuario eliminado", Toast.LENGTH_LONG).show()
                                        } catch (e: Exception) {
                                            Toast.makeText(context, "Error al eliminar el usuario", Toast.LENGTH_LONG).show()
                                        }
                                        showDialog = false
                                        userToDelete = null
                                    }
                                }
                            }
                        ) {
                            Text("Eliminar")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                                userToDelete = null
                            }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}