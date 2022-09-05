package pl.jsyty.bookworm.openlibrary

data class WorkSearchResult(
    val key: String,
    val title: String,
    val editionCount: Int,
    val firstPublishYear: Int?,
    val coverId: Int?,
    val authorNames: List<String>,
    val languages: List<String>,
)
