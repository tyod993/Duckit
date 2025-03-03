package com.company.duckit.domain.posts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostData(
    val id: String,
    val headline: String,
    val image: String,
    @SerialName("upvotes") var upVotes: Int,
    val author: String
)
