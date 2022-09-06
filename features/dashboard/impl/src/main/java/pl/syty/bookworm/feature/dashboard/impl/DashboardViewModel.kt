package pl.syty.bookworm.feature.dashboard.impl

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.orbitmvi.orbit.syntax.simple.*
import pl.jsyty.bookworm.openlibrary.*
import pl.syty.bookworm.infrastructure.async.*
import pl.syty.bookworm.infrastructure.viewmodel.BaseViewModel
import pl.syty.bookworm.infrastructure.viewmodel.async
import tangle.viewmodel.VMInject

class DashboardViewModel @VMInject constructor(
    private val openLibraryRepository: OpenLibraryRepository,
    private val coverUrlResolver: OpenLibraryCoverUrlResolver,
) : BaseViewModel<DashboardViewModel.State, Unit>(State()) {
    data class State(
        val searchQuery: String = "",
        val results: Async<ImmutableList<WorkSearchResultUiModel>> = Uninitialized,
    )

    @OptIn(FlowPreview::class)
    override fun onCreate() = intent {
        container.stateFlow
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(300)
            .collectLatest { query ->
                runSearch(query)
            }
    }

    fun updateSearchQuery(query: String) = intent {
        reduce { state.copy(searchQuery = query) }
    }

    fun rerunSearch() = intent {
        runSearch(state.searchQuery)
    }

    private suspend fun SimpleSyntax<State, Unit>.runSearch(searchQuery: String) {
        if (searchQuery.isBlank() || searchQuery.length < 2) {
            reduce {
                state.copy(
                    results = Success(
                        emptyList<WorkSearchResultUiModel>().toImmutableList()
                    )
                )
            }
            return
        }
        async {
            openLibraryRepository.searchForQuery(searchQuery)
                .map {
                    WorkSearchResultUiModel(
                        key = it.key,
                        title = it.title,
                        coverUrl = coverUrlResolver.resolve(it, CoverSize.SMALL),
                        authorName = it.authorNames.firstOrNull()
                    )
                }
                .toImmutableList()
        }.execute {
            state.copy(results = it)
        }
    }
}
