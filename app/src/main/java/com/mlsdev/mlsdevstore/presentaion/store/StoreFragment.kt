package com.mlsdev.mlsdevstore.presentaion.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.databinding.FragmentStoreBinding
import com.mlsdev.mlsdevstore.presentaion.bottom_navigation.MainActivity
import com.mlsdev.mlsdevstore.presentaion.categories.CategoriesActivity
import com.mlsdev.mlsdevstore.presentaion.fragment.BaseFragment
import javax.inject.Inject

class StoreFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var productsAdapter: RandomProductsAdapter
    lateinit var binding: FragmentStoreBinding
    lateinit var viewModel: StoreViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(StoreViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.refreshLayout.setOnRefreshListener(this)
        initRecyclerView()
        initErrorHandler()
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.app_name)
    }

    private fun initErrorHandler() {
        errorInViewHandler.observeNetworkError(this, viewModel.networkErrorLiveData)
        errorInViewHandler.observeCommonError(this, viewModel.commonErrorLiveData)
        errorInViewHandler.observeTechError(this, viewModel.technicalErrorLiveData)
        errorInViewHandler.observeAuthError(this, viewModel.authErrorLiveData)
    }

    private fun initRecyclerView() {
        productsAdapter = RandomProductsAdapter { item ->
            findNavController(this).navigate(StoreFragmentDirections.actionStoreFlowFragmentToProductFragment(item))
        }

        binding.rvProducts.adapter = productsAdapter
        (binding.rvProducts.layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = productsAdapter.getItemViewType(position)
                return when (viewType) {
                    VIEW_TYPE_FOOTER -> 2
                    VIEW_TYPE_HEADER -> 2
                    VIEW_TYPE_ITEM -> 1
                    else -> -1
                }
            }
        }

        productsAdapter.setOnClickListeners(
                View.OnClickListener { (activity as MainActivity).selectTab(R.id.navigation_item_account) },
                View.OnClickListener { findNavController(this).navigate(R.id.categories_fragment) },
                View.OnClickListener { viewModel.loadMoreItemsFromRandomCategory() }
        )

        viewModel.searchResultLiveData.observe(this, Observer {
            productsAdapter.setData(it)
            binding.rvProducts.notifyDataSetChanged()
        })

        viewModel.getProducts()
    }

    override fun onRefresh() {
        viewModel.refresh()
    }
}
