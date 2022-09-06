package pl.syty.bookworm.feature.dashboard.impl

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.orbitmvi.orbit.compose.collectAsState
import pl.syty.bookworm.infrastructure.async.Loading
import pl.syty.bookworm.infrastructure.drawableR
import pl.syty.bookworm.ui.async.FullscreenAsyncHandler
import pl.syty.bookworm.ui.modifiers.tapToClearFocus
import tangle.viewmodel.compose.tangleViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SearchPanel() {
    val viewModel = tangleViewModel<DashboardViewModel>()
    val state by viewModel.collectAsState()
    val placeholderPainter = painterResource(id = drawableR.ic_book)
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
                    items(asyncState, key = { item -> item.id }) { volume ->
                        ListItem(
                            modifier = Modifier.fillMaxWidth(),
                            text = { Text(text = volume.title) },
                            secondaryText = volume.subtitle?.let { { Text(text = it) } },
                            overlineText = volume.authorName?.let { { Text(text = it) } },
                            icon = {
                                if (volume.thumbnailUrl != null) {
                                    AsyncImage(
                                        model = volume.thumbnailUrl,
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier.size(48.dp),
                                        placeholder = placeholderPainter,
                                        error = placeholderPainter
                                    )
                                } else {
                                    Icon(
                                        painter = placeholderPainter,
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
