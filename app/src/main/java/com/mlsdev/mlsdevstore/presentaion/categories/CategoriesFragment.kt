package com.mlsdev.mlsdevstore.presentaion.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.databinding.FragmentCategoriesBinding
import com.mlsdev.mlsdevstore.presentaion.fragment.BaseFragment
import com.mlsdev.mlsdevstore.presentaion.store.CategoriesAdapter

class CategoriesFragment : BaseFragment() {

    lateinit var viewModel: CategoriesViewModel
    lateinit var binding: FragmentCategoriesBinding
    lateinit var adapter: CategoriesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoriesViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        errorInViewHandler.observeAuthError(this, viewModel.authErrorLiveData)
        errorInViewHandler.observeCommonError(this, viewModel.commonErrorLiveData)
        errorInViewHandler.observeTechError(this, viewModel.technicalErrorLiveData)
        errorInViewHandler.observeNetworkError(this, viewModel.networkErrorLiveData)
        viewModel.getRootCategories()
    }

    private fun initRecyclerView() {
        adapter = CategoriesAdapter { category, itemView ->
            Log.d("ON_ITEM_CLICK", "Category id: ${category.category.categoryId}; " +
                    "Category name: ${category.category.categoryName}")

            val action = CategoriesFragmentDirections.actionCategoriesFragmentToProductsFragment()
            action.categoryId = category.category.categoryId
            action.categoryName = category.category.categoryName
            findNavController().navigate(action)
        }
        binding.rvCategories.adapter = adapter
        viewModel.categories.observe(this, Observer {
            adapter.setData(it)
            binding.rvCategories.notifyDataSetChanged()
        })
    }

}