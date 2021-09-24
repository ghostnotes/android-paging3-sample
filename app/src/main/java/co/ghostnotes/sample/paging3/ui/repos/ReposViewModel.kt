package co.ghostnotes.sample.paging3.ui.repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import co.ghostnotes.sample.paging3.data.paging.RepoPagingSource
import co.ghostnotes.sample.paging3.domain.GetGitHubReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    //@IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getGitHubReposUseCase: GetGitHubReposUseCase,
) : ViewModel() {

    //private val _repos: MutableStateFlow<List<Repo>> = MutableStateFlow(emptyList())
    //val repos: StateFlow<List<Repo>> = _repos

    val repos = Pager(PagingConfig(pageSize = 20)) {
        RepoPagingSource(getGitHubReposUseCase)
    }.flow.cachedIn(viewModelScope)

    /*
    init {
        viewModelScope.launch(ioDispatcher) {
            getRepos()
        }
    }
     */

    /*
    private suspend fun getRepos() {
        try {
            _repos.value = getGitHubReposUseCase(1)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
     */
}
