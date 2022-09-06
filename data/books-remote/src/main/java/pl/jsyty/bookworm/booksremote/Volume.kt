package pl.jsyty.bookworm.booksremote

data class Volume(
    val id: String,
    val title: String,
    val subTitle: String? = null,
    val authors: List<String> = emptyList(),
    val publisher: String? = null,
    val description: String? = null,
    val pageCount: Int? = null,
    val language: String? = null,
    val imageLinks: ImageLinks,
) {
    data class ImageLinks(
        val thumbnail: String? = null,
        val small: String? = null,
        val medium: String? = null,
        val large: String? = null,
    )
}
