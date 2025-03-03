package com.company.duckit.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.company.duckit.R
import com.company.duckit.domain.actions.MainLoginButtonPressed
import com.company.duckit.domain.actions.MainPostDownvote
import com.company.duckit.domain.actions.MainPostUpvote
import com.company.duckit.domain.actions.MainScreenAction
import com.company.duckit.domain.state.MainScreenState
import com.company.duckit.domain.posts.PostData

@Composable
fun MainScreen(
    state: MainScreenState,
    submitAction: (MainScreenAction) -> Unit,
    loginScreen: @Composable () -> Unit
) {
    MaterialTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    onLoginClicked = { submitAction(MainLoginButtonPressed) },
                    isLoggedIn = state.isLoggedIn
                )
            }
        ) { innerPadding ->
            if (state.showLoginScreen) {
                loginScreen()
            }

            if (state.postsList.isEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }

            } else {
                LazyColumn(modifier = Modifier.padding(innerPadding)) {
                    items(state.postsList) { item ->
                        PostCard(
                            post = item,
                            onUpvoteClicked = { submitAction(MainPostUpvote(item)) },
                            onDownvoteClicked = { submitAction(MainPostDownvote(item)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(
    isLoggedIn: Boolean,
    onLoginClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 36.sp,
                color = colorResource(R.color.purple_500),
                fontWeight = FontWeight.Bold
            )
            if (!isLoggedIn) {
                Button(
                    onClick = onLoginClicked
                ) {
                    Text(stringResource(R.string.login))
                }
            }
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun PostCard(
    post: PostData,
    onUpvoteClicked: (PostData) -> Unit,
    onDownvoteClicked: (PostData) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(width = 1.dp, color = Color.Gray)
            .padding(6.dp)
    ) {
        Text(
            text = post.headline,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        AsyncImage(
            model = post.image,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { onUpvoteClicked(post) }
            ) {
                Icon(
                    Icons.Default.ThumbUp,
                    contentDescription = null
                )
            }
            Text(post.upVotes.toString())
            IconButton(
                onClick = { onDownvoteClicked(post) }
            ) {
                Icon(
                    modifier = Modifier.scale(scaleX = -1f, scaleY = -1f),
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview_PostCard() {
    PostCard(
        PostData(
            id = "",
            headline = "this is a fun picture of a duck",
            image = "https://www.hww.ca/wp-content/uploads/2014/11/Mallard-Duck-Ducklings_Asbed-Iskedjian.jpg",
            upVotes = 100,
            author = ""
        ),
        {},
        {}
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun Preview_MainScreen() {
    MainScreen(
        state = MainScreenState(
            postsList = mutableStateListOf(
                PostData(
                    id = "",
                    headline = "this is a fun picture of a duck",
                    image = "https://www.hww.ca/wp-content/uploads/2014/11/Mallard-Duck-Ducklings_Asbed-Iskedjian.jpg",
                    upVotes = 100,
                    author = ""
                ), PostData(
                    id = "",
                    headline = "this is a fun picture of a duck",
                    image = "https://www.hww.ca/wp-content/uploads/2014/11/Mallard-Duck-Ducklings_Asbed-Iskedjian.jpg",
                    upVotes = 100,
                    author = ""
                ), PostData(
                    id = "",
                    headline = "this is a fun picture of a duck",
                    image = "https://www.hww.ca/wp-content/uploads/2014/11/Mallard-Duck-Ducklings_Asbed-Iskedjian.jpg",
                    upVotes = 100,
                    author = ""
                ), PostData(
                    id = "",
                    headline = "this is a fun picture of a duck",
                    image = "https://www.hww.ca/wp-content/uploads/2014/11/Mallard-Duck-Ducklings_Asbed-Iskedjian.jpg",
                    upVotes = 100,
                    author = ""
                )
            ),
            isLoggedIn = false,
            showLoginScreen = false
        ),
        submitAction = {},
        loginScreen = {}
    )
}