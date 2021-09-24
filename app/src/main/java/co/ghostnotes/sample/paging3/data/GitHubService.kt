package co.ghostnotes.sample.paging3.data

import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("orgs/google/repos")
    suspend fun getRepos(
        @Query("per_page") perPage: Int = DEFAULT_PER_PAGE,
        @Query("page") page: Int,
    ): List<Repo>

    companion object {
        private const val DEFAULT_PER_PAGE = 20
    }
}

