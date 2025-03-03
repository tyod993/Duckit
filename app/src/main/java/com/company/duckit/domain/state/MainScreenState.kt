package com.company.duckit.domain.state

import com.company.duckit.domain.posts.PostData

data class MainScreenState(
    val postsList: MutableList<PostData>,
    val isLoggedIn: Boolean,
    val showLoginScreen: Boolean
)
