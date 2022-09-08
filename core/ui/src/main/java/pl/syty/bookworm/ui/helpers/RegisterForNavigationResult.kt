package pl.syty.bookworm.ui.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.lifecycle.LifecycleOwner
import pl.syty.bookworm.infrastructure.navigation.NavigationResult
import pl.syty.bookworm.ui.LocalChildFragmentManager

/**
 * Registers for result of navigation that is represented by given [navigationResult].
 *
 * @param T Type of result that will be returned
 * @param navigationResult Type of navigation result we're registering for
 * @param fragmentManager Fragment manager we want to use. Use [LocalChildFragmentManager] for dialogs (default) and [pl.syty.bookworm.ui.LocalParentFragmentManager] for results from sibling screens.
 * @param lifecycleOwner Lifecycle owner that will be used for listening. By default Fragments [LocalLifecycleOwner] is used.
 * @param onValueReceive Will be called with a parameter of type [T] when the result is passed to us.
 */
@Composable
fun <T : Any> RegisterForNavigationResult(
    navigationResult: NavigationResult<T>,
    onValueReceive: (T) -> Unit,
    fragmentManager: FragmentManager = LocalChildFragmentManager.current,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    LaunchedEffect(Unit) {
        fragmentManager.setFragmentResultListener(navigationResult.resultKey, lifecycleOwner) { _, bundle ->
            @Suppress("UNCHECKED_CAST", "DEPRECATION")
            val result = bundle.get(navigationResult.parameterKey) as T
            onValueReceive(result)
        }
    }
}

/**
 * Sets the navigation result for listeners
 *
 * @param T Type of result that will be passed
 * @param navigationResult Type of navigation result we're passing
 * @param value Result that will be passed
 */
fun <T : Any> Fragment.setNavigationResult(navigationResult: NavigationResult<T>, value: T) {
    setFragmentResult(
        navigationResult.resultKey,
        bundleOf(
            navigationResult.parameterKey to value
        )
    )
}
