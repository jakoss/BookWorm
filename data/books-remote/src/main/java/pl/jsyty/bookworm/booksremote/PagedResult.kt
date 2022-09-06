package pl.jsyty.bookworm.booksremote

data class PagedResult<T : Any>(
    val items: List<T>,
    val total: Int,
)
