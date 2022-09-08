package pl.jsyty.bookworm.feature.dashboard.impl

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.orbitmvi.orbit.syntax.simple.*
import pl.jsyty.bookworm.booksremote.*
import pl.jsyty.bookworm.infrastructure.async.*
import pl.jsyty.bookworm.infrastructure.viewmodel.BaseViewModel
import pl.jsyty.bookworm.infrastructure.viewmodel.async
import tangle.viewmodel.VMInject

class DashboardViewModel @VMInject constructor(
    private val booksRemoteRepository: BooksRemoteRepository,
) : BaseViewModel<DashboardViewModel.State, Unit>(State()) {
    data class State(
        val searchQuery: String = "",
        val results: Async<ImmutableList<VolumeSearchUiModel>> = Uninitialized,
    )

    @OptIn(FlowPreview::class)
    override fun onCreate() = intent {
        container.stateFlow
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(timeoutMillis = 300)
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
                        emptyList<VolumeSearchUiModel>().toImmutableList()
                    )
                )
            }
            return
        }
        async {
            booksRemoteRepository.searchForQuery(searchQuery)
                .map {
                    VolumeSearchUiModel(
                        id = it.id,
                        title = it.title,
                        subtitle = it.subTitle,
                        thumbnailUrl = it.imageLinks.thumbnail,
                        authorName = it.authors.firstOrNull()
                    )
                }
                .toImmutableList()
        }.execute {
            state.copy(results = it)
        }
    }
}
