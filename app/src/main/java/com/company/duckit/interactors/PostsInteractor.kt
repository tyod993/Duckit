package com.company.duckit.interactors

import com.company.duckit.domain.auth.UserToken
import com.company.duckit.domain.posts.PostData
import com.company.duckit.domain.interfaces.IPostsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class PostsInteractor(private val service: IPostsService) {

    fun getPosts(): Flow<MutableList<PostData>> = flow {
        Timber.e("get22")
        val res = service.getPosts()
        Timber.e("get221")
        emit(res.posts.toMutableList())
    }

    fun upvotePost(token: UserToken, post: PostData): Flow<PostData> = flow {
        val res = service.upvotePost(token, post.id)
        emit(post.copy(upVotes = res.upVotes))
    }

    fun downvotePost(token: UserToken, post: PostData): Flow<PostData> = flow {
        val res = service.downvotePost(token, post.id)
        emit(post.copy(upVotes = res.upVotes))
    }
}