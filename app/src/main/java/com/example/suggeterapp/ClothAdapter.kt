package com.example.suggeterapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suggeterapp.databinding.ItemImageBinding


class ClothAdapter(private val ClothList:List<Cloth>) :RecyclerView.Adapter<ClothAdapter.ClothViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothViewHolder {
     val bind=ItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ClothViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ClothViewHolder, position: Int) =holder.getImage(ClothList[position])

     override fun getItemCount(): Int = ClothList.size

    class ClothViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
      fun getImage(cloth:Cloth){
          binding.clothImage.setImageResource(cloth.imageSource)
      }
    }
}