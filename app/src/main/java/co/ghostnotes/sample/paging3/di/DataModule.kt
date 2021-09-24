package co.ghostnotes.sample.paging3.di

import co.ghostnotes.sample.paging3.data.GitHubRepositoryImpl
import co.ghostnotes.sample.paging3.domain.GitHubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindGitHubRepository(impl: GitHubRepositoryImpl): GitHubRepository
}