package pl.jsyty.bookworm.booksremote.impl.dtos

import kotlinx.serialization.Serializable

@Serializable
data class VolumeDto(
    val id: String,
    val volumeInfo: InfoDto,
) {
    @Serializable
    data class InfoDto(
        val title: String,
        val subTitle: String? = null,
        val authors: List<String> = emptyList(),
        val publisher: String? = null,
        val description: String? = null,
        val pageCount: Int? = null,
        val language: String? = null,
        val imageLinks: ImageLinksDto = ImageLinksDto(),
    ) {
        @Serializable
        data class ImageLinksDto(
            val thumbnail: String? = null,
            val small: String? = null,
            val medium: String? = null,
            val large: String? = null,
        )
    }
}
