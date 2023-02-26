package com.artinsoft.pets.presentation.subcategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artinsoft.pets.R
import com.artinsoft.pets.databinding.ItemSubCategoryBinding
import com.artinsoft.pets.domain.model.SubCategoryModel
import com.bumptech.glide.Glide

class SubCategoryAdapter: ListAdapter<SubCategoryModel,
        SubCategoryAdapter.SubCategoryViewHolder>(SubCategoryDiffUtil()) {

    override fun submitList(list: List<SubCategoryModel?>?) {
        super.submitList(list?.toList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        return SubCategoryViewHolder(
            ItemSubCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
       holder.bind(getItem(position))
    }

    inner class SubCategoryViewHolder(
        private val binding: ItemSubCategoryBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: SubCategoryModel){
            Glide
                .with(binding.root.context)
                .load(item.url)
                .centerCrop()
                .placeholder(R.drawable.ic_cat)
                .into(binding.imageCat)
        }
    }
    
    class SubCategoryDiffUtil: DiffUtil.ItemCallback<SubCategoryModel>(){
        override fun areItemsTheSame(oldItem: SubCategoryModel, newItem: SubCategoryModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SubCategoryModel,
            newItem: SubCategoryModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}