package com.company.duckit.domain.interfaces

import com.company.duckit.domain.posts.api.GetPostsResponse
import com.company.duckit.domain.auth.UserToken
import com.company.duckit.domain.posts.api.PostVoteResponse

interface IPostsService {
    suspend fun getPosts(): GetPostsResponse
    suspend fun addPost(token: UserToken): Boolean
    suspend fun upvotePost(token: UserToken, postId: String): PostVoteResponse
    suspend fun downvotePost(token: UserToken, postId: String): PostVoteResponse
}