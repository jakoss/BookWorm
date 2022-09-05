package pl.jsyty.bookworm.openlibrary.impl.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedResultDto<T : Any>(
    @SerialName("docs")
    val items: List<T>,
    val start: Int,
    @SerialName("numFound")
    val total: Int,
    @SerialName("numFoundExact")
    val totalExact: Boolean,
)
