package pl.syty.bookworm

import android.app.Application
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import pl.syty.bookworm.infrastructure.di.AppScope
import javax.inject.Singleton

@Singleton
@MergeComponent(AppScope::class)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: Application,
        ): AppComponent
    }
}
