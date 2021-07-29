package com.acompworld.teamconnect.api.model.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeedItem(
    @Json(name = "attachmentpath")
    val attachmentpath: String?,
    @Json(name = "linkurl")
    val linkurl: String?,
    @Json(name = "postedby")
    val postedby: String = "",
    @Json(name = "postedon")
    val postedon: String?,
    @Json(name = "titleEng")
    val titleEng: String?,
    @Json(name = "titleHin")
    val titleHin: String?,
    @Json(name = "type")
    val type: String?,


//v2....
    @Json(name = "LinkURL")
    val linkURL: String?,
    @Json(name = "PostedOn")
    val postedOn_v2: String?,
    @Json(name = "TitleEnglish")
    val titleEnglish_v2: String="",
    @Json(name = "TitleHindi")
    val titleHindi_v2: String="",
    @Json(name = "Type")
    val type_v2: String=""
)