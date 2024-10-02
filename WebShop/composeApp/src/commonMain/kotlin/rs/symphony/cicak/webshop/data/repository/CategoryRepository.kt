package rs.symphony.cicak.webshop.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import rs.symphony.cicak.webshop.domain.Category

interface CategoryRepository {

    fun getRootCategories(): StateFlow<List<Category>>
    suspend fun fetchRootCategories()
}

class CategoryRepositoryFake(
    private val appModel: AppModel
) : CategoryRepository {

    override fun getRootCategories(): StateFlow<List<Category>> = appModel.categories

    override suspend fun fetchRootCategories() {
        delay(2000)

        val fakeCategories = (1..10).map { i ->
            Category(id = i.toString(), name = "Category $i", image = "")
        }

        //throw Exception("Failed to fetch categories")

        appModel.updateCategories(fakeCategories)
    }
}
