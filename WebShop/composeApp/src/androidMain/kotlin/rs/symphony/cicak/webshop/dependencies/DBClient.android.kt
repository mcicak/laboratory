package rs.symphony.cicak.webshop.dependencies

import android.content.Context

actual class DBClient(
    private val context: Context
): IDBClient
