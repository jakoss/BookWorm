package pl.jsyty.bookworm.booksremote.impl

import pl.jsyty.bookworm.booksremote.impl.dtos.PagedResultDto
import pl.jsyty.bookworm.booksremote.impl.dtos.VolumeDto
import retrofit2.http.GET
import retrofit2.http.Query

interface VolumesService {
    @GET(".")
    suspend fun searchForQuery(@Query("q") query: String): PagedResultDto<VolumeDto>
}
