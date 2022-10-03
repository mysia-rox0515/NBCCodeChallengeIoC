package com.example.nbccodechallengeioc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nbccodechallengeioc.adapter.FruitsAdapter
import com.example.nbccodechallengeioc.databinding.ActivityMainBinding
import com.example.nbccodechallengeioc.model.ResultState
import com.example.nbccodechallengeioc.viewmodel.FruitsViewModel

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[FruitsViewModel::class.java]
    }

    private val fruitAdapter by lazy {
        FruitsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fruitRv.apply {
            layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
            adapter = fruitAdapter
        }

        viewModel.fruits.observe(this) {
            when (it) {
                is ResultState.SUCCESS -> {
                    Log.d(TAG, "onCreate: ${it.data}")
                    fruitAdapter.update(it.data)
                }
                is ResultState.LOADING -> {
                    Log.d(TAG, "onCreate: is loading")
                }
                is ResultState.ERROR -> {
                    Log.e(TAG, "onCreate: ${it.error.localizedMessage}", it.error)
                }
            }
        }
    }
}