package pl.syty.bookworm.feature.dashboard.impl

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.sharp.MenuBook
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import pl.syty.bookworm.feature.dashboard.DashboardDirection
import pl.syty.bookworm.infrastructure.async.Fail
import pl.syty.bookworm.infrastructure.async.Loading
import pl.syty.bookworm.ui.BaseDirectableComposeFragment
import pl.syty.bookworm.ui.LocalNavigationController
import pl.syty.bookworm.ui.async.FullscreenAsyncHandler
import pl.syty.bookworm.ui.modifiers.tapToClearFocus
import pl.syty.bookworm.ui.theme.BookWormTheme
import tangle.viewmodel.compose.tangleViewModel

class DashboardFragment : BaseDirectableComposeFragment<DashboardDirection>() {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val bottomSheetState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val navigationController = LocalNavigationController.current

        BackHandler {
            if (bottomSheetState.isVisible) {
                scope.launch { bottomSheetState.hide() }
            } else {
                navigationController.pop()
            }
        }

        val viewModel = tangleViewModel<DashboardViewModel>()
        val state by viewModel.collectAsState()

        ModalBottomSheetLayout(
            modifier = Modifier.fillMaxSize(),
            sheetState = bottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetContent = {
                LaunchedEffect(state.results) {
                    if (state.results is Fail && bottomSheetState.currentValue == ModalBottomSheetValue.HalfExpanded) {
                        scope.launch { bottomSheetState.animateTo(ModalBottomSheetValue.Expanded) }
                    }
                }
                SearchPanel(
                    state = state,
                    onSearchType = viewModel::updateSearchQuery,
                    onRetryAction = viewModel::rerunSearch
                )
            }) {
            Scaffold(
                bottomBar = {
                    BottomAppBar(cutoutShape = CircleShape) {

                    }
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        scope.launch { if (!bottomSheetState.isVisible) bottomSheetState.show() }
                    }) {
                        Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                    }
                },
                floatingActionButtonPosition = FabPosition.Center,
                isFloatingActionButtonDocked = true,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(text = "App")
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun SearchPanel(
        state: DashboardViewModel.State,
        onSearchType: (String) -> Unit = {},
        onRetryAction: () -> Unit = {},
    ) {
        val placeholderVector = rememberVectorPainter(image = Icons.Sharp.MenuBook)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .tapToClearFocus()
        ) {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = onSearchType,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null
                    )
                })
            Spacer(modifier = Modifier.height(4.dp))
            if (state.results is Loading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            FullscreenAsyncHandler(
                state = state.results,
                retryAction = onRetryAction,
                loading = {},
                uninitialized = {},
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

    @Preview(showBackground = true)
    @Composable
    private fun DefaultPreview() {
        BookWormTheme {
            SearchPanel(DashboardViewModel.State())
        }
    }
}
