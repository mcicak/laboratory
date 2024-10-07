package rs.symphony.cicak.webshop.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.Source
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.StateFlow
import rs.symphony.cicak.webshop.domain.User

interface UserRepository {
    val user: StateFlow<User?>
    suspend fun addUserToFirestore(firebaseUser: FirebaseUser)
}

class UserRepositoryImpl(private val appModel: AppModel) : UserRepository {

    private val db = Firebase.firestore
    override val user: StateFlow<User?> = appModel.user

    override suspend fun addUserToFirestore(firebaseUser: FirebaseUser) {
        val uid = firebaseUser.uid
        val userData = hashMapOf(
            "displayName" to (firebaseUser.displayName ?: "N/A"),
            "email" to firebaseUser.email,
            "createdAt" to FieldValue.serverTimestamp
        )

        val docSnapshot = db.collection("users").document(uid).get(Source.DEFAULT)
        if (!docSnapshot.exists) {
            db.collection("users").document(uid).set(userData)
        }
        appModel.setUser(User(firebaseUser.displayName ?: "N/A", firebaseUser.email ?: ""))
    }
}