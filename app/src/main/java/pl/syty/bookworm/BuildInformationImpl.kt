package pl.syty.bookworm

import com.squareup.anvil.annotations.ContributesBinding
import pl.syty.bookworm.infrastructure.BuildInformation
import pl.syty.bookworm.infrastructure.di.AppScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ContributesBinding(scope = AppScope::class)
class BuildInformationImpl @Inject constructor() : BuildInformation {
    override val apiUrl: String = BuildConfig.API_URL
}
