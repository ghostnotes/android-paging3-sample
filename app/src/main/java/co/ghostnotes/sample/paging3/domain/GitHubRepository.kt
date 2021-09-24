package co.ghostnotes.sample.paging3.domain

import co.ghostnotes.sample.paging3.data.Repo

interface GitHubRepository {
    suspend fun getRepos(page: Int): List<Repo>
}