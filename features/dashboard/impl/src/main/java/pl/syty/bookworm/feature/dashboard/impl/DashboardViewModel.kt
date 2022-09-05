package pl.syty.bookworm.feature.dashboard.impl

import pl.syty.bookworm.infrastructure.navigation.NavigationController
import pl.syty.bookworm.infrastructure.viewmodel.BaseViewModel
import tangle.viewmodel.VMInject

class DashboardViewModel @VMInject constructor(
    private val navigationController: NavigationController,
) : BaseViewModel<DashboardViewModel.State, Unit>(State()) {
    data class State(
        val returnedMessage: String? = null,
        val name: String = "",
    )
}
