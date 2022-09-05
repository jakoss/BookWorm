package pl.syty.bookworm.infrastructure.navigation.processor

import com.squareup.kotlinpoet.ClassName
import org.jetbrains.kotlin.name.FqName

object FqNames {
    val direction =
        FqName("pl.syty.bookworm.infrastructure.navigation.Direction")
    val baseDirectableComposeFragment = FqName("pl.syty.bookworm.ui.BaseDirectableComposeFragment")
    val baseDirectableComposeDialogFragment = FqName("pl.syty.bookworm.ui.BaseDirectableComposeDialogFragment")
    val androidFragment = FqName("androidx.fragment.app.Fragment")
}

object ClassNames {
    val appScope = ClassName("pl.syty.bookworm.infrastructure.di", "AppScope")
    val androidFragment = ClassName("androidx.fragment.app", "Fragment")
    val injectAnnotation = ClassName("javax.inject", "Inject")
    val directionKey = ClassName("pl.syty.bookworm.infrastructure.navigation", "DirectionKey")
    val fragmentFactory = ClassName("pl.syty.bookworm.infrastructure.navigation", "FragmentFactory")
}
