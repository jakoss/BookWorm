package pl.jsyty.bookworm.infrastructure.navigation

import com.squareup.anvil.annotations.ContributesTo
import pl.jsyty.bookworm.infrastructure.di.AppScope

@ContributesTo(AppScope::class)
interface NavigationComponent {
    fun navigationEventsProvider(): NavigationEventsProvider
    fun navigationControllerProvider(): NavigationController
    fun navigationFragmentResolver(): NavigationFragmentResolver
}
