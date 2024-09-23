package rs.symphony.cicak.webshop.dependencies

interface MyRepository {
    fun helloWorld(): String

    fun getInitials(fullName: String): String
}

class MyRepositoryImpl(
    private val dbClient: IDBClient
) : MyRepository {

    override fun helloWorld(): String {
        return "Hello World 123!"
    }

    override fun getInitials(fullName: String): String {
        val names = fullName
            .split(" ")
            .filter { it.isNotBlank() }

        return when {
            names.size == 1 && names.first().length <= 1 -> {
                names.first().first().toString().uppercase()
            }

            names.size == 1 && names.first().length > 1 -> {
                val name = names.first().uppercase()
                "${name.first()}${name[1].uppercase()}"
            }

            else -> {
                "${names.first().first()}${names.last().first()}"
            }
        }
    }
}