package com.company.duckit.data.posts

import com.company.duckit.domain.posts.api.GetPostsResponse
import com.company.duckit.domain.auth.UserToken
import com.company.duckit.domain.interfaces.IPostsService
import com.company.duckit.domain.posts.api.PostVoteResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.*


class PostsService(private var client: HttpClient) : IPostsService {

    override suspend fun getPosts(): GetPostsResponse {
        val response = client.get("/posts")
        return when (response.status.value) {
            200 -> Json.decodeFromString<GetPostsResponse>(response.body())
            else -> GetPostsResponse(emptyList())
        }
    }

    override suspend fun addPost(token: UserToken): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun upvotePost(token: UserToken, postId: String): PostVoteResponse {
        val response = client.post("/posts/:$postId/upvote") {
            headers.append(HttpHeaders.Authorization, "Bearer ${token.token}")
        }
        return when (response.status.value) {
            200 -> Json.decodeFromString<PostVoteResponse>(response.body())
            else -> PostVoteResponse(-1)
        }
    }

    override suspend fun downvotePost(token: UserToken, postId: String): PostVoteResponse {
        val response = client.post("/posts/:$postId/downvote") {
            headers {
                append(HttpHeaders.Authorization, "Bearer: $token")
            }
        }
        return when (response.status.value) {
            200 -> Json.decodeFromString<PostVoteResponse>(response.body())
            else -> PostVoteResponse(-1)
        }
    }
}