package pl.jsyty.bookworm.infrastructure.navigation.processor

import com.squareup.kotlinpoet.ClassName
import org.jetbrains.kotlin.name.FqName

object FqNames {
    val baseDirectableComposeFragment = FqName("pl.jsyty.bookworm.ui.BaseDirectableComposeFragment")
    val baseDirectableComposeDialogFragment = FqName("pl.jsyty.bookworm.ui.BaseDirectableComposeDialogFragment")
    val androidFragment = FqName("androidx.fragment.app.Fragment")
}

object ClassNames {
    val appScope = ClassName("pl.jsyty.bookworm.infrastructure.di", "AppScope")
    val androidFragment = ClassName("androidx.fragment.app", "Fragment")
    val injectAnnotation = ClassName("javax.inject", "Inject")
    val directionKey = ClassName("pl.jsyty.bookworm.infrastructure.navigation", "DirectionKey")
    val fragmentFactory = ClassName("pl.jsyty.bookworm.infrastructure.navigation", "FragmentFactory")
}
