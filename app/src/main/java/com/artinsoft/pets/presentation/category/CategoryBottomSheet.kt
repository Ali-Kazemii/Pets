package com.artinsoft.pets.presentation.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.artinsoft.pets.databinding.BottomSheetCategoryBinding
import com.artinsoft.pets.utils.autoCleared
import com.artinsoft.pets.utils.setBackStackData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoryBottomSheet: BottomSheetDialogFragment() {

    private var binding by autoCleared<BottomSheetCategoryBinding>()
    private var categoryAdapter: CategoryAdapter?= null
    private val args by navArgs<CategoryBottomSheetArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = BottomSheetCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
        categoryAdapter = CategoryAdapter()
        categoryAdapter?.onItemClick = {
            setBackStackData(
                KEY_PASS_CATEGORY,
                it,
                doBack = true
            )
        }
        categoryAdapter?.submitList(args.listCategory.list.toMutableList())
        binding.rclCategory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoryAdapter
        }
    }

    override fun onDestroyView() {
        categoryAdapter?.submitList(null)
        categoryAdapter = null
        super.onDestroyView()
    }

    companion object{
        const val KEY_PASS_CATEGORY = "KEY_PASS_CATEGORY"
    }
}