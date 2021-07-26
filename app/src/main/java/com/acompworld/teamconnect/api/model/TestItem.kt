package com.acompworld.teamconnect.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TestItem(
    @Json(name = "attachmentpath")
    val attachmentpath: String?,
    @Json(name = "linkurl")
    val linkurl: String?,
    @Json(name = "postedby")
    val postedby: String="",
    @Json(name = "postedon")
    val postedon: String="",
    @Json(name = "titleEng")
    val titleEng: String="",
    @Json(name = "titleHin")
    val titleHin: String="",
    @Json(name = "type")
    val type: String=""
)