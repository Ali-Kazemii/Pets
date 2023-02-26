package com.artinsoft.pets.presentation.subcategory

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artinsoft.pets.R
import com.artinsoft.pets.databinding.ContainSubCategoryBinding
import com.artinsoft.pets.databinding.FragmentSubCategoryBinding
import com.artinsoft.pets.databinding.LayoutWaitingBinding
import com.artinsoft.pets.domain.model.Categories
import com.artinsoft.pets.domain.model.CategoryModel
import com.artinsoft.pets.presentation.category.CategoryBottomSheet.Companion.KEY_PASS_CATEGORY
import com.artinsoft.pets.presentation.category.CategoryViewModel
import com.artinsoft.pets.utils.alertDialog
import com.artinsoft.pets.utils.autoCleared
import com.artinsoft.pets.utils.collectWhenStarted
import com.artinsoft.pets.utils.getBackStackDataFromDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubCategoryFragment : Fragment() {

    private val viewModel: CategoryViewModel by viewModels()
    private var binding by autoCleared<FragmentSubCategoryBinding>()
    private var containBinding by autoCleared<ContainSubCategoryBinding>()
    private var waitBinding by autoCleared<LayoutWaitingBinding>()

    private var subCategoryAdapter: SubCategoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubCategoryBinding.inflate(layoutInflater)
        containBinding = binding.contain
        waitBinding = binding.layoutWait
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initObserver()
        initCallback()
        initOnClickListener()
    }

    private fun initList() {
        subCategoryAdapter = SubCategoryAdapter()
        containBinding.rclSubCategory.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = subCategoryAdapter
        }
        containBinding.rclSubCategory.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastItemVisible = layoutManager.findLastCompletelyVisibleItemPosition()
                val endHasBeenReached = lastItemVisible + 1 >= totalItemCount
                containBinding.buttonLoadMore.isVisible = (totalItemCount > 0 && endHasBeenReached)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun initObserver() {
        viewModel.progress.observe(viewLifecycleOwner) {
            containBinding.loading.isVisible = it
        }
        viewModel.title.observe(viewLifecycleOwner) {
            it?.let {
                binding.textViewTitle.text = "Cat Category ($it)"
            }
        }
        viewModel.listSubCategory.collectWhenStarted(viewLifecycleOwner) { response ->
            response.let { list ->
                subCategoryAdapter?.submitList(list.toMutableList())
                if (list.isNotEmpty())
                    waitBinding.root.isVisible = false
            }
        }

        viewModel.failure.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                requireContext().alertDialog(
                    "403 forbidden",
                    "You don't have access to this url" +
                            ".\n\nPlease make sure of your internet connection and try again"
                )
            } else
                requireContext().alertDialog(
                    "Error",
                    it
                )
        }
    }

    private fun initOnClickListener() {
        binding.buttonMenu.setOnClickListener {
            viewModel.listCategory?.let { model ->
                findNavController().navigate(
                    SubCategoryFragmentDirections.actionSubCategoryFragmentToCategoryBottomSheet(
                        Categories(model)
                    )
                )
            } ?: showNoDataMessage()
        }
        containBinding.buttonLoadMore.setOnClickListener {
            viewModel.loadMore()
            containBinding.buttonLoadMore.isVisible = false
        }
    }

    private fun showNoDataMessage() {
        Toast.makeText(requireContext(), getString(R.string.data_is_not_exist), Toast.LENGTH_SHORT)
            .show()
    }

    @SuppressLint("SetTextI18n")
    private fun initCallback() {
        getBackStackDataFromDialog<CategoryModel?>(
            R.id.subCategoryFragment,
            KEY_PASS_CATEGORY,
        ) { item ->
            subCategoryAdapter?.submitList(null)
            waitBinding.root.isVisible = true
            viewModel.getSubCategory(item?.id)
            item?.name?.let { name ->
                binding.textViewTitle.text = "Cat Category (${name})"
            }
        }
    }

    override fun onDestroyView() {
        subCategoryAdapter?.submitList(null)
        subCategoryAdapter = null
        super.onDestroyView()
    }
}