package rs.symphony.cicak.webshop.dependencies

import androidx.lifecycle.ViewModel

class MyViewModel(
    private val repository: MyRepository
) : ViewModel() {

    fun getHelloWorldString(): String {
        return repository.helloWorld()
    }
}