package pl.jsyty.bookworm.openlibrary.impl

import com.squareup.anvil.annotations.ContributesBinding
import pl.jsyty.bookworm.openlibrary.OpenLibraryRepository
import pl.jsyty.bookworm.openlibrary.WorkSearchResult
import pl.syty.bookworm.infrastructure.di.AppScope
import javax.inject.Inject

@ContributesBinding(scope = AppScope::class)
class OpenLibraryRepositoryImpl @Inject constructor(
    private val openLibrarySearchService: OpenLibrarySearchService,
    private val openLibraryMapper: OpenLibraryMapper
) : OpenLibraryRepository {
    override suspend fun searchForQuery(query: String): List<WorkSearchResult> {
        val result = openLibrarySearchService.searchForQuery(query)
        return openLibraryMapper.fromSearchResult(result).items
    }
}
