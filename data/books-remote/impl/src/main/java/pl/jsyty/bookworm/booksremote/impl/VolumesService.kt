package pl.jsyty.bookworm.booksremote.impl

import pl.jsyty.bookworm.booksremote.impl.dtos.PagedResultDto
import pl.jsyty.bookworm.booksremote.impl.dtos.VolumeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VolumesService {
    @GET(".?maxResults=40&printType=books&startIndex=0&projection=lite")
    suspend fun searchForQuery(@Query("q") query: String): PagedResultDto<VolumeDto>

    @GET("{id}?projection=lite")
    suspend fun getVolumeById(@Path("id") id: String): VolumeDto
}
