package com.mason.rssreader.data.model

import kotlinx.serialization.Serializable

/**
 * Data class representing the response from the news API.
 *
 * @property status The status of the response, e.g., "ok" or "error".
 * @property feed The feed information, including URL, title, etc.
 * @property items The list of news items.
 */
@Serializable
data class NewsResponse(
    val status: String?,
    val feed: Feed?,
    val items: List<Item>
)

/**
 * Data class representing the feed information.
 *
 * @property url The URL of the feed.
 * @property title The title of the feed.
 * @property link The link to the feed.
 * @property author The author of the feed.
 * @property description The description of the feed.
 * @property image The URL of the image associated with the feed.
 */
@Serializable
data class Feed(
    val url: String?,
    val title: String?,
    val link: String?,
    val author: String?,
    val description: String?,
    val image: String?
)

/**
 * Data class representing a news item.
 *
 * @property title The title of the news item.
 * @property pubDate The publication date of the news item.
 * @property link The link to the news item.
 * @property guid The unique identifier for the news item.
 * @property author The author of the news item.
 * @property thumbnail The URL of the thumbnail image for the news item.
 * @property description The description of the news item.
 * @property content The content of the news item.
 * @property enclosure The enclosure containing media information for the news item.
 * @property categories The list of categories the news item belongs to.
 */
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

/**
 * Data class representing an enclosure containing media information for a news item.
 *
 * @property link The URL of the media file.
 * @property type The type of the media file, e.g., "image/jpeg".
 * @property thumbnail The URL of the thumbnail image for the media file.
 */
@Serializable
data class Enclosure(
    val link: String?,
    val type: String?,
    val thumbnail: String?
)
