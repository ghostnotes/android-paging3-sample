package co.ghostnotes.sample.paging3.data

import co.ghostnotes.sample.paging3.domain.GitHubRepository
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val gitHubService: GitHubService
) : GitHubRepository {
    override suspend fun getRepos(page: Int): List<Repo> {
        return gitHubService.getRepos(page = page)
    }
}