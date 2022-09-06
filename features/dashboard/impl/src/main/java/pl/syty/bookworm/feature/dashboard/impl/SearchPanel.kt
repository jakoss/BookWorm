package pl.syty.bookworm.feature.dashboard.impl

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.sharp.MenuBook
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.orbitmvi.orbit.compose.collectAsState
import pl.syty.bookworm.infrastructure.async.Loading
import pl.syty.bookworm.ui.async.FullscreenAsyncHandler
import pl.syty.bookworm.ui.modifiers.tapToClearFocus
import tangle.viewmodel.compose.tangleViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SearchPanel() {
    val viewModel = tangleViewModel<DashboardViewModel>()
    val state by viewModel.collectAsState()
    val placeholderVector = rememberVectorPainter(image = Icons.Sharp.MenuBook)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .tapToClearFocus()
    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = viewModel::updateSearchQuery,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (state.results is Loading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        FullscreenAsyncHandler(
            state = state.results,
            retryAction = viewModel::rerunSearch,
            loading = {},
            uninitialized = {}
        ) { asyncState ->
            if (asyncState.isEmpty()) {
                Text(text = "No search results")
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(asyncState, key = { item -> item.key }) { work ->
                        ListItem(
                            modifier = Modifier.fillMaxWidth(),
                            text = { Text(text = work.title) },
                            secondaryText = work.authorName?.let { { Text(text = it) } },
                            icon = {
                                if (work.coverUrl != null) {
                                    AsyncImage(
                                        model = work.coverUrl,
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier.size(48.dp),
                                        placeholder = placeholderVector,
                                        error = placeholderVector
                                    )
                                } else {
                                    Icon(
                                        Icons.Sharp.MenuBook,
                                        contentDescription = null,
                                        modifier = Modifier.size(48.dp)
                                    )
                                }
                            }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}
