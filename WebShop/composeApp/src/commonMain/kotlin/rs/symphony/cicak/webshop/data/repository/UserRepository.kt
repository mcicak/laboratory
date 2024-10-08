package rs.symphony.cicak.webshop.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.Source
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.StateFlow
import rs.symphony.cicak.webshop.domain.ProductId
import rs.symphony.cicak.webshop.domain.User

interface UserRepository {
    val user: StateFlow<User?>
    suspend fun addUserToFirestore(firebaseUser: FirebaseUser)
    suspend fun toggleFavorite(productId: ProductId)
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

        var favoriteProducts = emptyList<ProductId>()
        val docSnapshot = db.collection("users").document(uid).get(Source.DEFAULT)
        if (!docSnapshot.exists) {
            db.collection("users").document(uid).set(userData)
        } else {
            val favList = docSnapshot.get("favorite") as? List<ProductId>
            favoriteProducts = favList ?: emptyList()
        }

        appModel.setUser(
            User(
                id = firebaseUser.uid,
                name = firebaseUser.displayName ?: "N/A",
                email = firebaseUser.email ?: "",
                favorite = favoriteProducts
            )
        )

        db.collection("users").document(uid).snapshots.collect { snapshot ->
            val favorites = snapshot.get<List<ProductId>>("favorite")

            appModel.setUser(
                appModel.user.value?.copy(
                    favorite = favorites ?: emptyList()
                )
            )
        }
    }

    override suspend fun toggleFavorite(productId: ProductId) {
        user.value?.let { user ->
            val original = user.favorite
            val favorite = if (original.contains(productId)) {
                original.minus(productId)
            } else {
                original.plus(productId)
            }

            appModel.setUser(
                user.copy(
                    favorite = favorite
                )
            )

            try {
                db.collection("users").document(user.id)
                    .update(
                        "favorite" to favorite
                    )
            } catch (e: Exception) {
                println("Error updating favorite: ${e.message}")
                appModel.setUser(
                    user.copy(
                        favorite = original
                    )
                )
            }
        }
    }
}