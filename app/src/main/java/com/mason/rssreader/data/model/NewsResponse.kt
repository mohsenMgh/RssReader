package com.mason.rssreader.data.model

import kotlinx.serialization.Serializable


@Serializable
data class NewsResponse(
    val status: String?,
    val feed: Feed?,
    val items: List<Item>
)

@Serializable
data class Feed(
    val url: String?,
    val title: String?,
    val link: String?,
    val author: String?,
    val description: String?,
    val image: String?
)

@Serializable
data class Item(
    val title: String,
    val pubDate: String?,
    val link: String?,
    val guid: String?,
    val author: String?,
    val thumbnail: String?,
    val description: String?,
    val content: String?,
    val enclosure: Enclosure?,
    val categories: List<String>?
)

@Serializable
data class Enclosure(
    val link: String?,
    val type: String?,
    val thumbnail: String?
)





