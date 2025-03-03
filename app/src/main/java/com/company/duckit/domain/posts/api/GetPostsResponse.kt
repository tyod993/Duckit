package com.company.duckit.domain.posts.api

import com.company.duckit.domain.posts.PostData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPostsResponse(@SerialName("Posts") val posts: List<PostData>)
