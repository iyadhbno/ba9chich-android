package com.example.ba9chich

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.ba9chich.models.ChatUser
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.name

class MainActivityChat : AppCompatActivity() {
    private lateinit var navController: NavController
    private val client = ChatClient.instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_chat)

        navController = findNavController(R.id.navHostFragment)

        if (navController.currentDestination?.label.toString().contains("login")) {
            val currentUser = client.getCurrentUser()
            if (currentUser != null) {
                val user = ChatUser(currentUser.name, currentUser.id)
                val action = LoginFragmentDirections.actionLoginFragmentToChannelFragment(user)
                navController.navigate(action)
            }
        }

    }
}