package pl.syty.bookworm.feature.dashboard.impl

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import pl.jsyty.bookworm.openlibrary.OpenLibraryRepository
import pl.jsyty.bookworm.openlibrary.WorkSearchResult
import pl.syty.bookworm.infrastructure.async.Async
import pl.syty.bookworm.infrastructure.async.Uninitialized
import pl.syty.bookworm.infrastructure.viewmodel.BaseViewModel
import pl.syty.bookworm.infrastructure.viewmodel.async
import tangle.viewmodel.VMInject

class DashboardViewModel @VMInject constructor(
    private val openLibraryRepository: OpenLibraryRepository,
) : BaseViewModel<DashboardViewModel.State, Unit>(State()) {
    data class State(
        val searchQuery: String = "",
        val results: Async<List<WorkSearchResult>> = Uninitialized,
    )

    @OptIn(FlowPreview::class)
    override fun onCreate() = intent {
        container.stateFlow
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(300)
            .collectLatest { query ->
                async {
                    if (query.isNotBlank()) {
                        openLibraryRepository.searchForQuery(query)
                    } else {
                        emptyList()
                    }
                }.execute {
                    state.copy(results = it)
                }
            }
    }

    fun updateSearchQuery(query: String) = intent {
        reduce { state.copy(searchQuery = query) }
    }
}
