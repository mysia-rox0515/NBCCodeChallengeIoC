package com.example.nbccodechallengeioc.model

import com.google.gson.annotations.SerializedName

data class Fruit(
    @SerializedName("family")
    val family: String,
    @SerializedName("genus")
    val genus: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("nutritions")
    val nutritions: Nutritions,
    @SerializedName("order")
    val order: String
)

data class Nutritions(
    @SerializedName("calories")
    val calories: Double,
    @SerializedName("carbohydrates")
    val carbohydrates: Double,
    @SerializedName("fat")
    val fat: Double,
    @SerializedName("protein")
    val protein: Double,
    @SerializedName("sugar")
    val sugar: Double
)

