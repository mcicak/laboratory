package rs.symphony.cicak.webshop.dependencies

import platform.CoreData.NSManagedObjectContext
import platform.UIKit.UIViewController


actual class DBClient: IDBClient {
    fun get() {
        val moc = NSManagedObjectContext

        val vc = UIViewController()

    }
}