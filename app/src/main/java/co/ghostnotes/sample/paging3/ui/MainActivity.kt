package co.ghostnotes.sample.paging3.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import co.ghostnotes.sample.paging3.Screen
import co.ghostnotes.sample.paging3.data.Repo
import co.ghostnotes.sample.paging3.ui.repos.ReposViewModel
import co.ghostnotes.sample.paging3.ui.theme.Paging3SampleTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val reposViewModel: ReposViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Paging3SampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Main(reposViewModel)
                }
            }
        }
    }
}

@Composable
fun Main(reposViewModel: ReposViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Repos.route) {
        composable(Screen.Repos.route) { Repos(reposViewModel) }
    }
}


@Composable
fun Repos(reposViewModel: ReposViewModel) {
    Repos(reposViewModel.repos)
}

@Composable
fun Repos(repos: Flow<PagingData<Repo>>) {
    val swipeRefreshState = rememberSwipeRefreshState(false)
    val repoPagingItems: LazyPagingItems<Repo> = repos.collectAsLazyPagingItems()

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { repoPagingItems.refresh() }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
        ) {
            items(repoPagingItems) { repo ->
                RepoItem(repo)
            }

            repoPagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingItem(modifier = Modifier.fillParentMaxSize()) }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem(modifier = Modifier.fillParentMaxWidth()) }
                    }
                }
            }
        }
    }
}

@Composable
fun RepoItem(repo: Repo?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = repo?.name ?: "Unknown name",
            style = MaterialTheme.typography.h6,
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = repo?.description ?: "[No description]",
            style = MaterialTheme.typography.caption,
        )
    }
}

@Composable
fun LoadingItem(modifier: Modifier) {
    Box(modifier = modifier.padding(16.dp)) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}
