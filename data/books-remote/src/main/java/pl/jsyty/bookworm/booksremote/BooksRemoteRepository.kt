package pl.jsyty.bookworm.booksremote

interface BooksRemoteRepository {
    suspend fun searchForQuery(query: String): List<Volume>
    suspend fun getVolumeById(id: String): Volume
}
