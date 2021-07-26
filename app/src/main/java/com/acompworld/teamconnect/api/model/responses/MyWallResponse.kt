package com.acompworld.teamconnect.api.model.responses


import com.acompworld.teamconnect.api.model.entities.Bannerlist
import com.acompworld.teamconnect.api.model.entities.Circular
import com.acompworld.teamconnect.api.model.entities.Latesupdate
import com.acompworld.teamconnect.api.model.entities.Notice
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyWallResponse(
    @Json(name = "bannerlist")
    val bannerlist: List<Bannerlist>,
    @Json(name = "circulars")
    val circulars: List<Circular>,
    @Json(name = "latesupdates")
    val latesupdates: List<Latesupdate>,
    @Json(name = "news")
    val news: List<Any>,
    @Json(name = "notices")
    val notices: List<Notice>,
    @Json(name = "others")
    val others: List<Any>,
    @Json(name = "projectcode")
    val projectcode: String,
    @Json(name = "projectname")
    val projectname: String
)