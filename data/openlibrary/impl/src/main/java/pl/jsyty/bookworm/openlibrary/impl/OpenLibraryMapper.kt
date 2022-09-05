package pl.jsyty.bookworm.openlibrary.impl

import org.mapstruct.Mapper
import pl.jsyty.bookworm.openlibrary.PagedResult
import pl.jsyty.bookworm.openlibrary.WorkSearchResult
import pl.jsyty.bookworm.openlibrary.impl.dtos.PagedResultDto
import pl.jsyty.bookworm.openlibrary.impl.dtos.WorkSearchResultDto

@Mapper
interface OpenLibraryMapper {
    fun fromSearchResult(result: PagedResultDto<WorkSearchResultDto>): PagedResult<WorkSearchResult>

    fun fromDto(dto: WorkSearchResultDto): WorkSearchResult
}
