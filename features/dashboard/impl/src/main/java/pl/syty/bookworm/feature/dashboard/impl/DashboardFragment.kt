package pl.syty.bookworm.feature.dashboard.impl

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.orbitmvi.orbit.compose.collectAsState
import pl.syty.bookworm.feature.dashboard.DashboardDirection
import pl.syty.bookworm.ui.*
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
            DashboardPanel()
        }
    }

    @Composable
    private fun DashboardPanel() {
    }

    @Preview(showBackground = true)
    @Composable
    private fun DefaultPreview() {
        ArchitectureTemplateTheme {
            DashboardPanel()
        }
    }
}
