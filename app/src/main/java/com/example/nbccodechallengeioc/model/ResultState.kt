package com.example.nbccodechallengeioc.model

sealed class ResultState {
    object LOADING : ResultState()
    data class SUCCESS(val data: List<Fruit>) : ResultState()
    data class ERROR(val error: Exception) : ResultState()
}
