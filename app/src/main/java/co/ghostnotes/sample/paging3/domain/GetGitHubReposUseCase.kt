package co.ghostnotes.sample.paging3.domain

import co.ghostnotes.sample.paging3.data.Repo
import javax.inject.Inject

class GetGitHubReposUseCase @Inject constructor(
    private val gitHubRepository: GitHubRepository
) {
    suspend operator fun invoke(page: Int): List<Repo> {
        return gitHubRepository.getRepos(page)
    }
}
