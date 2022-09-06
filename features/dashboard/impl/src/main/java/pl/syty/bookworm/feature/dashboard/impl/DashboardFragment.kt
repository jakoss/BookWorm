package pl.syty.bookworm.feature.dashboard.impl

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import pl.syty.bookworm.feature.dashboard.DashboardDirection
import pl.syty.bookworm.infrastructure.async.Fail
import pl.syty.bookworm.ui.BaseDirectableComposeFragment
import pl.syty.bookworm.ui.LocalNavigationController
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
                SearchPanel()
            }
        ) {
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
                isFloatingActionButtonDocked = true
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
}
