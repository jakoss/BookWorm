package pl.jsyty.bookworm.openlibrary.impl

import com.squareup.anvil.annotations.ContributesBinding
import pl.jsyty.bookworm.openlibrary.*
import pl.syty.bookworm.infrastructure.di.AppScope
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class OpenLibraryCoverUrlResolverImpl @Inject constructor() :
    OpenLibraryCoverUrlResolver {
    override fun resolve(workSearchResult: WorkSearchResult, size: CoverSize): String? {
        val coverId = workSearchResult.coverId ?: return null

        return "https://covers.openlibrary.org/b/id/$coverId-${size.size}.jpg"
    }
}
