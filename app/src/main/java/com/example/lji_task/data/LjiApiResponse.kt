package com.example.lji_task.data


import com.google.gson.annotations.SerializedName


data class LjiApiResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("total_count")
    val totalCount: Int
)