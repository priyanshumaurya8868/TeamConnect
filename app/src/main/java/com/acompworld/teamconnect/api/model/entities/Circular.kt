package com.acompworld.teamconnect.api.model.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Circular(
    @Json(name = "attachmentpath")
    val attachmentpath: String?,
    @Json(name = "PostedOn")
    val postedOn: String?,
    @Json(name = "postedby")
    val postedby: String?,
    @Json(name = "TitleEnglish")
    val titleEnglish: String="",
    @Json(name = "TitleHindi")
    val titleHindi: String=""
)