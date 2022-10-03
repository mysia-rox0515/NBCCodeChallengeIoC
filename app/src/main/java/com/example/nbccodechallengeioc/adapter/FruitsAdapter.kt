package com.example.nbccodechallengeioc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbccodechallengeioc.databinding.FruitItemBinding
import com.example.nbccodechallengeioc.model.Fruit

class FruitsAdapter(
    private val dataSet: MutableList<Fruit> = mutableListOf()
) : RecyclerView.Adapter<FruitViewHolder>() {

    fun update(newFruits: List<Fruit>) {
        dataSet.clear()
        dataSet.addAll(newFruits)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder =
        FruitViewHolder(
            FruitItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) =
        holder.bind(dataSet[position])

    override fun getItemCount(): Int = dataSet.size
}

class FruitViewHolder(
    private val binding: FruitItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(fruit: Fruit) {
        binding.nameFruit.text = fruit.name
    }
}