package com.company.duckit.domain.actions

import com.company.duckit.domain.posts.PostData

sealed interface MainScreenAction
data object MainLoginButtonPressed : MainScreenAction
data object MainLoginScreenDismissed : MainScreenAction
data class MainPostUpvote(val post: PostData) : MainScreenAction
data class MainPostDownvote(val post: PostData) : MainScreenAction