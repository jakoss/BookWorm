package pl.jsyty.bookworm.openlibrary

interface OpenLibraryCoverUrlResolver {
    fun resolve(workSearchResult: WorkSearchResult, size: CoverSize): String?
}
