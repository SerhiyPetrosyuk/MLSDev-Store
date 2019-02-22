package com.mlsdev.mlsdevstore.presentaion.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.databinding.FragmentCartBinding
import com.mlsdev.mlsdevstore.presentaion.fragment.BaseFragment

class CartFragment : BaseFragment() {
    lateinit var itemsAdapter: ItemsAdapter
    lateinit var binding: FragmentCartBinding
    val viewModel: CartViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(CartViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        itemsAdapter = ItemsAdapter { productId ->
            viewModel.removeItem(productId)
        }
        binding.itemsRecycler.adapter = itemsAdapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.productsInCart.observe(this, Observer { pagedList ->
            itemsAdapter.submitList(pagedList)
            binding.viewModel?.cartIsEmpty?.set(pagedList.isEmpty())
        })
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.navigation_title_cart)
    }
}
