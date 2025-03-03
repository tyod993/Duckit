package com.company.duckit.controllers

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.duckit.domain.actions.MainLoginButtonPressed
import com.company.duckit.domain.actions.MainLoginScreenDismissed
import com.company.duckit.domain.actions.MainPostDownvote
import com.company.duckit.domain.actions.MainPostUpvote
import com.company.duckit.domain.actions.MainScreenAction
import com.company.duckit.domain.state.MainScreenState
import com.company.duckit.domain.posts.PostData
import com.company.duckit.interactors.PostsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val postInteractor: PostsInteractor,
    private val userTokenProvider: UserTokenProvider
) : ViewModel() {

    val state = MutableStateFlow(
        MainScreenState(
            mutableStateListOf(),
            isLoggedIn = false,
            showLoginScreen = false
        )
    )


    init {
        viewModelScope.launch(Dispatchers.IO) {
            postInteractor.getPosts().collect { posts ->
                state.update {
                    state.value.copy(postsList = posts)
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            while(true) {
                userTokenProvider.userTokenFlow.collect { token ->
                    if(token.token.isNotEmpty()){
                        state.update { state.value.copy(showLoginScreen = false) }
                    }
                    state.update { state.value.copy(isLoggedIn = token.token.isNotEmpty()) }
                }
            }
        }
    }

    fun submitMainScreenAction(action: MainScreenAction) {
        when (action) {
            MainLoginButtonPressed -> handleLoginPressed()
            MainLoginScreenDismissed -> handleLoginDismissed()
            is MainPostDownvote -> handlePostDownvote(action.post)
            is MainPostUpvote -> handlePostUpvote(action.post)
        }
    }

    private fun handleLoginPressed() {
        state.update { state.value.copy(showLoginScreen = true) }
    }

    private fun handleLoginDismissed() {
        state.update { state.value.copy(showLoginScreen = false) }
    }

    private fun handlePostUpvote(post: PostData) {
        if (userTokenProvider.userTokenFlow.value.isLoggedIn()) {
            viewModelScope.launch {
                postInteractor.upvotePost(userTokenProvider.userTokenFlow.value, post).collect{ data ->
                }
            }
        } else {
            //TODO add a model that navigates to login
        }

    }

    private fun handlePostDownvote(post: PostData) {
        if (userTokenProvider.userTokenFlow.value.isLoggedIn()) {
            viewModelScope.launch {
                postInteractor.downvotePost(userTokenProvider.userTokenFlow.value, post)
            }
        } else {
            //TODO add a model that navigates to login
        }
    }

}