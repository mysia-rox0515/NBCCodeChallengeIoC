package com.example.nbccodechallengeioc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbc_injection_sdk.api.getApi
import com.example.nbccodechallengeioc.model.ResultState
import com.example.nbccodechallengeioc.rest.FruitsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FruitsViewModel(
    private val repository: FruitsRepository? = getApi(),
    private val ioDispatchers: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    init {
        retrieveFruits()
    }

    private val _fruits: MutableLiveData<ResultState> = MutableLiveData(ResultState.LOADING)
    val fruits: LiveData<ResultState> get() = _fruits

    private fun retrieveFruits() {
        viewModelScope.launch(ioDispatchers) {
            repository?.getAllFruits()?.collect {
                _fruits.postValue(it)
            }
        }
    }
}