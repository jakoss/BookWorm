package pl.syty.bookworm.feature.dashboard.impl

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.orbitmvi.orbit.compose.collectAsState
import pl.syty.bookworm.feature.dashboard.DashboardDirection
import pl.syty.bookworm.ui.*
import pl.syty.bookworm.ui.async.FullscreenAsyncHandler
import pl.syty.bookworm.ui.theme.ArchitectureTemplateTheme
import tangle.viewmodel.compose.tangleViewModel

class DashboardFragment : BaseDirectableComposeFragment<DashboardDirection>() {
    @Composable
    override fun Content() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val viewModel = tangleViewModel<DashboardViewModel>()
            val state by viewModel.collectAsState()
            DashboardPanel(state = state, onSearchType = viewModel::updateSearchQuery)
        }
    }

    @Composable
    private fun DashboardPanel(
        state: DashboardViewModel.State,
        onSearchType: (String) -> Unit = {}
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(value = state.searchQuery, onValueChange = onSearchType)
            Spacer(modifier = Modifier.height(4.dp))
            FullscreenAsyncHandler(state = state.results, retryAction = { /*TODO*/ }) {
                if (it.isEmpty()){
                    Text(text = "No search results")
                }else{
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(it){ work ->
                            Text(text = work.title)
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun DefaultPreview() {
        ArchitectureTemplateTheme {
            DashboardPanel(DashboardViewModel.State())
        }
    }
}
