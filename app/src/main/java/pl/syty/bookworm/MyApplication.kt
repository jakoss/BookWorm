package pl.syty.bookworm

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import okhttp3.OkHttpClient
import pl.syty.bookworm.core.networking.NiddlerHandler
import pl.syty.bookworm.infrastructure.di.AppScope
import pl.syty.bookworm.infrastructure.di.ComponentHolder
import tangle.inject.TangleGraph
import tangle.inject.TangleScope
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@TangleScope(AppScope::class)
open class MyApplication : Application(), ImageLoaderFactory {

    @Inject
    lateinit var httpClient: OkHttpClient

    override fun onCreate() {
        super.onCreate()

        @Suppress("KotlinConstantConditions")
        if (BuildConfig.BUILD_TYPE != "release") {
            Timber.plant(Timber.DebugTree())
        }
        NiddlerHandler.init(this)

        setupPreInjection()
        val appComponent = DaggerAppComponent.factory().create(this)
        ComponentHolder.components += appComponent
        TangleGraph.add(appComponent)

        TangleGraph.inject(this)

        NiddlerHandler.start()
    }

    override fun onTerminate() {
        NiddlerHandler.stop()

        super.onTerminate()
    }

    /**
     * Inheriting class can override this function to make some custom initialization.
     * This is called BEFORE Dagger container is setup.
     */
    protected open fun setupPreInjection() {
        // nothing here, only for override
    }

    override fun newImageLoader() = ImageLoader.Builder(this)
        .okHttpClient(httpClient)
        .diskCache(
            DiskCache.Builder()
                .directory(File(this.cacheDir, "images_cache"))
                .maxSizeBytes(1024 * 1024 * 100) // 500 MB of image cache for now
                .build()
        )
        .memoryCache(
            MemoryCache.Builder(this)
                .maxSizeBytes(1024 * 1024 * 20) // 20 MB of memory image cache
                .build()
        )
        .crossfade(true)
        .build()
}
