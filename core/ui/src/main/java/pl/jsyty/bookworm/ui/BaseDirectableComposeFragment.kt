package pl.jsyty.bookworm.ui

import pl.jsyty.bookworm.infrastructure.navigation.ARGUMENT_KEY
import pl.jsyty.bookworm.infrastructure.navigation.Direction

/**
 * Marks this [androidx.fragment.app.Fragment] as directable.
 * Anvil process plugin will automatically bind this [androidx.fragment.app.Fragment] to given [T] direction.
 *
 * @param T Direcation that will be bind to this [androidx.fragment.app.Fragment]
 */
abstract class BaseDirectableComposeFragment<T : Direction> : BaseComposeFragment() {
    protected val direction: T
        get() = @Suppress("DEPRECATION") arguments?.getParcelable(ARGUMENT_KEY) as? T
            ?: error("No argument found")
}
