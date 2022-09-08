package pl.jsyty.bookworm

import com.pluto.Pluto
import com.pluto.plugins.exceptions.PlutoExceptionsPlugin
import com.pluto.plugins.logger.PlutoLoggerPlugin
import com.pluto.plugins.logger.PlutoTimberTree
import com.pluto.plugins.network.PlutoInterceptor
import com.pluto.plugins.network.PlutoNetworkPlugin
import okhttp3.OkHttpClient
import pl.jsyty.bookworm.core.networking.OkHttpBuilderStep
import pl.jsyty.bookworm.core.networking.OkHttpBuilderSteps
import pl.jsyty.bookworm.infrastructure.di.AppScope
import tangle.inject.TangleScope
import timber.log.Timber

@Suppress("unused")
@TangleScope(AppScope::class)
class DebugApplication : MyApplication() {
    override fun setupPreInjection() {
        OkHttpBuilderSteps.addBuilder(object : OkHttpBuilderStep {
            override fun addBuildStep(builder: OkHttpClient.Builder) {
                builder.addInterceptor(PlutoInterceptor())
            }
        })
    }

    override fun onCreate() {
        super.onCreate()

        Pluto.Installer(this)
            .addPlugin(PlutoLoggerPlugin("logger"))
            .addPlugin(PlutoExceptionsPlugin("exceptions"))
            .addPlugin(PlutoNetworkPlugin("network"))
            .install()

        Timber.plant(PlutoTimberTree())
    }
}
