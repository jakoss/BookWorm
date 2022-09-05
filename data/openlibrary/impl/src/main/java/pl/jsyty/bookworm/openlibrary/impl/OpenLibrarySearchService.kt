package pl.jsyty.bookworm.openlibrary.impl

import pl.jsyty.bookworm.openlibrary.impl.dtos.PagedResultDto
import pl.jsyty.bookworm.openlibrary.impl.dtos.WorkSearchResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibrarySearchService {
    @GET(".")
    suspend fun searchForQuery(@Query("q") query: String): PagedResultDto<WorkSearchResultDto>
}
