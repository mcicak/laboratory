package rs.symphony.cicak.webshop.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import rs.symphony.cicak.webshop.domain.Category

interface CategoryRepository {

    fun getRootCategories(): Flow<List<Category>>
}

class CategoryRepositoryFirestore(
    private val appModel: AppModel
) : CategoryRepository {

    private val firestore = Firebase.firestore

    override fun getRootCategories() = flow {
        firestore.collection("categories").snapshots.collect { snapshot ->
            val categories = snapshot.documents.map { doc ->
                doc.data<Category>()
            }.sortedBy { it.order }
            emit(categories)
        }
    }
}

class CategoryRepositoryFake(
    private val appModel: AppModel
) : CategoryRepository {

    override fun getRootCategories(): StateFlow<List<Category>> = appModel.categories
}
