package pl.jsyty.bookworm.booksremote.impl.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedResultDto<T : Any>(
    val items: List<T>,
    @SerialName("totalItems")
    val total: Int,
)
