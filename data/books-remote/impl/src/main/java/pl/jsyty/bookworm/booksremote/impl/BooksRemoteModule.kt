package pl.jsyty.bookworm.booksremote.impl

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import pl.syty.bookworm.core.networking.RetrofitFactory
import pl.syty.bookworm.infrastructure.di.AppScope
import javax.inject.Singleton

@Suppress("Unused")
@Module
@ContributesTo(AppScope::class)
object BooksRemoteModule {
    @Singleton
    @Provides
    fun provideService(retrofitFactory: RetrofitFactory): VolumesService {
        return retrofitFactory.create("volumes")
    }
}
