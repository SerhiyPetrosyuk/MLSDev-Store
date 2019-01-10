package com.mlsdev.mlsdevstore.presentaion.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.databinding.FragmentCartBinding
import com.mlsdev.mlsdevstore.presentaion.fragment.BaseFragment
import javax.inject.Inject

class CartFragment : BaseFragment() {
    @Inject
    lateinit var itemsAdapter: ItemsAdapter
    lateinit var binding: FragmentCartBinding
    lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CartViewModel::class.java)
        lifecycle.addObserver(viewModel)
        lifecycle.addObserver(itemsAdapter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.itemsRecycler.adapter = itemsAdapter
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        lifecycle.removeObserver(itemsAdapter)
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.navigation_title_cart)
    }
}
