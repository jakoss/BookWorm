package pl.jsyty.bookworm.openlibrary.impl

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import pl.syty.bookworm.core.networking.RetrofitFactory
import pl.syty.bookworm.infrastructure.di.AppScope
import pl.syty.bookworm.infrastructure.mapping.getMapper
import javax.inject.Singleton

@Suppress("Unused")
@Module
@ContributesTo(AppScope::class)
object OpenLibraryModule {
    @Singleton
    @Provides
    fun provideOpenLibrarySearchService(retrofitFactory: RetrofitFactory): OpenLibrarySearchService {
        return retrofitFactory.create("search.json")
    }

    @Singleton
    @Provides
    fun providerMapper(): OpenLibraryMapper = getMapper()
}
