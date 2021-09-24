package co.ghostnotes.sample.paging3

sealed class Screen(val route: String) {
    object Repos : Screen("repos")
}
