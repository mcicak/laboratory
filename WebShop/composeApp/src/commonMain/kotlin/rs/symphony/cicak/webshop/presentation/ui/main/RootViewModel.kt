package rs.symphony.cicak.webshop.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rs.symphony.cicak.webshop.data.repository.UserRepository
import rs.symphony.cicak.webshop.domain.User

class RootViewModel(private val userRepository: UserRepository) : ViewModel() {

    val user: StateFlow<User?> = userRepository.user

    fun handleUserLogin(firebaseUser: FirebaseUser) {
        viewModelScope.launch {
            try {
                userRepository.addUserToFirestore(firebaseUser)
            } catch (e: Exception) {
                println("Failed to add user to Firestore: ${e.message}")
            }
        }
    }
}
