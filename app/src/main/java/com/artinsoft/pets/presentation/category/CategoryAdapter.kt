package com.artinsoft.pets.presentation.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artinsoft.pets.databinding.ItemCategoryBinding
import com.artinsoft.pets.domain.model.CategoryModel

class CategoryAdapter: ListAdapter<CategoryModel, CategoryAdapter.CategoryViewHolder>(CategoryDiffUtil()) {

    var onItemClick: ((CategoryModel?) -> Unit) = {}

    override fun submitList(list: MutableList<CategoryModel?>?) {
        super.submitList(list?.toList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class CategoryViewHolder(
        private val binding: ItemCategoryBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: CategoryModel){
            if(adapterPosition == itemCount - 1)
                binding.divider.isVisible = false

            binding.textViewCategory.text = item.name
            binding.root.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }

    class CategoryDiffUtil: DiffUtil.ItemCallback<CategoryModel>(){
        override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CategoryModel,
            newItem: CategoryModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}