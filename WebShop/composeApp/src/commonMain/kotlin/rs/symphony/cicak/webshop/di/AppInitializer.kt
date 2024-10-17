package rs.symphony.cicak.webshop.di

import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import io.kamel.core.config.DefaultCacheSize
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.fileFetcher
import io.kamel.core.config.httpFetcher
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import io.kamel.image.config.imageBitmapDecoder
import io.kamel.image.config.imageVectorDecoder
import io.kamel.image.config.svgDecoder
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.http.isSuccess
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

object AppInitializer {

    fun initialize(config: KoinAppDeclaration? = null) {
        initKoin(config)
        initKamel()
        initNotifier()
    }

    private fun initKoin(config: KoinAppDeclaration?) {
        startKoin {
            config?.invoke(this)
            modules(sharedModule, platformModule)
        }
    }

    private fun initKamel() {
        KamelConfig {
            takeFrom(KamelConfig.Default)

            // Sets the number of images to cache
            imageBitmapCacheSize = DefaultCacheSize

            // adds an ImageBitmapDecoder
            imageBitmapDecoder()

            // adds an ImageVectorDecoder (XML)
            imageVectorDecoder()

            // adds an SvgDecoder (SVG)
            svgDecoder()

            // adds a FileFetcher
            fileFetcher()

            // Configures Ktor HttpClient
            httpFetcher {
                // httpCache is defined in kamel-core and configures the ktor client
                // to install a HttpCache feature with the implementation provided by Kamel.
                // The size of the cache can be defined in Bytes.
                httpCache(10 * 1024 * 1024  /* 10 MiB */)

                //                        defaultRequest {
                //                            url("https://firebasestorage.googleapis.com/")
                //                            cacheControl(CacheControl.MaxAge(maxAgeSeconds = 10000))
                //                        }

                install(HttpRequestRetry) {
                    maxRetries = 3
                    retryIf { httpRequest, httpResponse ->
                        !httpResponse.status.isSuccess()
                    }
                }

                // Requires adding "io.ktor:ktor-client-logging:$ktor_version"
                //                        Logging {
                //                            level = LogLevel.INFO
                //                            logger = Logger.SIMPLE
                //                        }
            }
        }
    }

    private fun initNotifier() {
        NotifierManager.addListener(object : NotifierManager.Listener {

            override fun onNewToken(token: String) {
                super.onNewToken(token)
                println("TOKEN: $token")
            }

            override fun onPayloadData(data: PayloadData) {
                super.onPayloadData(data)
                println("onPayloadData: $data")
            }

            override fun onPushNotification(title: String?, body: String?) {
                super.onPushNotification(title, body)
                println("onPushNotification: $title, $body")
            }

            override fun onNotificationClicked(data: PayloadData) {
                super.onNotificationClicked(data)
                println("onNotificationClicked: $data")
            }
        })
    }
}