package com.acompworld.teamconnect.api.model.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Bannerlist(
    @Json(name = "bannerfile")
    val bannerfile: String?,
    @Json(name = "captionEng")
    val captionEng: String="",
    @Json(name = "captionHin")
    val captionHin: String=""
)