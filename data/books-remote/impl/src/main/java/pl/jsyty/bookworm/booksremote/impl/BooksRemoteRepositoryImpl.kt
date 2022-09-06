package pl.jsyty.bookworm.booksremote.impl

import com.squareup.anvil.annotations.ContributesBinding
import pl.jsyty.bookworm.booksremote.BooksRemoteRepository
import pl.jsyty.bookworm.booksremote.Volume
import pl.jsyty.bookworm.booksremote.impl.dtos.VolumeDto
import pl.syty.bookworm.infrastructure.di.AppScope
import javax.inject.Inject

@ContributesBinding(scope = AppScope::class)
class BooksRemoteRepositoryImpl @Inject constructor(
    private val volumesService: VolumesService,
) : BooksRemoteRepository {
    override suspend fun searchForQuery(query: String): List<Volume> {
        val result = volumesService.searchForQuery(query)

        return result.items.map {
            mapDtoToDomain(it)
        }
    }

    override suspend fun getVolumeById(id: String): Volume {
        val result = volumesService.getVolumeById(id)

        return mapDtoToDomain(result)
    }

    private fun mapDtoToDomain(volumeDto: VolumeDto): Volume {
        return Volume(
            id = volumeDto.id,
            title = volumeDto.volumeInfo.title,
            subTitle = volumeDto.volumeInfo.subTitle,
            authors = volumeDto.volumeInfo.authors,
            publisher = volumeDto.volumeInfo.publisher,
            description = volumeDto.volumeInfo.description,
            pageCount = volumeDto.volumeInfo.pageCount,
            language = volumeDto.volumeInfo.language,
            imageLinks = Volume.ImageLinks(
                thumbnail = volumeDto.volumeInfo.imageLinks.thumbnail,
                small = volumeDto.volumeInfo.imageLinks.small,
                medium = volumeDto.volumeInfo.imageLinks.medium,
                large = volumeDto.volumeInfo.imageLinks.large
            )
        )
    }
}
