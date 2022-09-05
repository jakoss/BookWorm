package pl.jsyty.bookworm.openlibrary

interface OpenLibraryRepository {
    suspend fun searchForQuery(query: String): List<WorkSearchResult>
}
