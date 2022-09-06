package pl.jsyty.bookworm.booksremote.impl

import com.squareup.anvil.annotations.ContributesBinding
import pl.jsyty.bookworm.booksremote.BooksRemoteRepository
import pl.jsyty.bookworm.booksremote.Volume
import pl.syty.bookworm.infrastructure.di.AppScope
import javax.inject.Inject

@ContributesBinding(scope = AppScope::class)
class BooksRemoteRepositoryImpl @Inject constructor(
    private val volumesService: VolumesService,
) : BooksRemoteRepository {
    override suspend fun searchForQuery(query: String): List<Volume> {
        val result = volumesService.searchForQuery(query)

        return result.items.map {
            Volume(
                id = it.id,
                title = it.volumeInfo.title,
                subTitle = it.volumeInfo.subTitle,
                authors = it.volumeInfo.authors,
                publisher = it.volumeInfo.publisher,
                description = it.volumeInfo.description,
                pageCount = it.volumeInfo.pageCount,
                language = it.volumeInfo.language,
                imageLinks = Volume.ImageLinks(
                    thumbnail = it.volumeInfo.imageLinks.thumbnail,
                    small = it.volumeInfo.imageLinks.small,
                    medium = it.volumeInfo.imageLinks.medium,
                    large = it.volumeInfo.imageLinks.large
                )
            )
        }
    }
}
