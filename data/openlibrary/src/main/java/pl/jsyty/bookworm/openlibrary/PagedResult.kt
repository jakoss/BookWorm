package pl.jsyty.bookworm.openlibrary

data class PagedResult<T: Any>(
    val items: List<T>,
    val start: Int,
    val total: Int,
    val totalExact: Boolean
)
