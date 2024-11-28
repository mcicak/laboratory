import assertk.assertThat
import assertk.assertions.isEqualTo
import org.koin.compose.koinInject
import rs.symphony.cicak.webshop.dependencies.DBClient
import rs.symphony.cicak.webshop.dependencies.IDBClient
import rs.symphony.cicak.webshop.dependencies.MyRepository
import rs.symphony.cicak.webshop.dependencies.MyRepositoryImpl
import kotlin.test.BeforeTest
import kotlin.test.Test

class FakeDbClient: IDBClient {

}

class MyRepositoryImplTest {

    lateinit var sut: MyRepository

    @BeforeTest
    fun setup() {
        sut = MyRepositoryImpl(FakeDbClient())
    }

    @Test
    fun testHelloWorld() {
        sut.helloWorld()
        assertThat(sut.helloWorld()).isEqualTo("Hello World 123!")
    }
}
