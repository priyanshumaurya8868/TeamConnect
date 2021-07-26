package com.acompworld.teamconnect.api.model.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Notice(
    @Json(name = "attachmentpath")
    val attachmentpath: String?,
    @Json(name = "postedon")
    val postedon: String?,
    @Json(name = "titleEng")
    val titleEng: String?,
    @Json(name = "titleHin")
    val titleHin: String?
)