package co.ghostnotes.sample.paging3.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.ghostnotes.sample.paging3.data.Repo
import co.ghostnotes.sample.paging3.domain.GetGitHubReposUseCase
import timber.log.Timber
import javax.inject.Inject

class RepoPagingSource @Inject constructor(
    val getGitHubReposUseCase: GetGitHubReposUseCase
) : PagingSource<Int, Repo>() {
    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        Timber.d("--> GET getRefreshKey(): ${state.anchorPosition}")
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return try {
            val nextPage = if (params.key == null || params.key!! <= 0) {
                1
            } else {
                params.key!!
            }
            Timber.d("--> GET key=${params.key}, nextPage=$nextPage")

            val repos = getGitHubReposUseCase(nextPage)
            LoadResult.Page(
                data = repos,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (repos.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}