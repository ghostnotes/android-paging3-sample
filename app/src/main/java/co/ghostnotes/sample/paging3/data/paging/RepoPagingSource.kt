package co.ghostnotes.sample.paging3.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.ghostnotes.sample.paging3.data.Repo
import co.ghostnotes.sample.paging3.domain.GetGitHubReposUseCase
import javax.inject.Inject

class RepoPagingSource @Inject constructor(
    val getGitHubReposUseCase: GetGitHubReposUseCase
) : PagingSource<Int, Repo>() {
    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return try {
            val nextPage = params.key ?: 1
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