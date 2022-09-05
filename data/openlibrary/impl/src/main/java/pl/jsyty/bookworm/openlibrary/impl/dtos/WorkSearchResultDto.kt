package pl.jsyty.bookworm.openlibrary.impl.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkSearchResultDto(
    val key: String,
    val title: String,
    @SerialName("edition_count")
    val editionCount: Int = 1,
    @SerialName("first_publish_year")
    val firstPublishYear: Int? = null,
    @SerialName("cover_i")
    val coverId: Int? = null,
    @SerialName("author_name")
    val authorNames: List<String> = emptyList(),
    @SerialName("language")
    val languages: List<String> = emptyList()
)
