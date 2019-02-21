package com.mlsdev.mlsdevstore.presentaion.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.databinding.FragmentProductBinding
import com.mlsdev.mlsdevstore.presentaion.adapter.ProductGalleryAdapter
import com.mlsdev.mlsdevstore.presentaion.fragment.BaseFragment
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys
import javax.inject.Inject

class ProductFragment : BaseFragment() {

    @Inject
    lateinit var galleryAdapter: ProductGalleryAdapter
    lateinit var viewModel: ProductDetailsViewModel
    lateinit var binding: FragmentProductBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductDetailsViewModel::class.java)
        binding.viewModel = viewModel
        lifecycle.addObserver(viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productImagesGallery.adapter = galleryAdapter
        viewModel.setProductDetailsData(arguments)

        viewModel.imagesLiveData.observe(this, Observer { galleryAdapter.setImages(it) })
        viewModel.descriptionStateLiveData.observe(this, Observer {
            if (it) {
                val data = Bundle().apply { putString(ExtrasKeys.KEY_PRODUCT_DESCRIPTION, viewModel.description.get()) }
                val bottomSheet = DescriptionBottomSheetFragment()
                bottomSheet.arguments = data
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
        })

        initErrorHandler()
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initErrorHandler() {
        errorInViewHandler.observeNetworkError(this, viewModel.networkErrorLiveData)
        errorInViewHandler.observeCommonError(this, viewModel.commonErrorLiveData)
        errorInViewHandler.observeTechError(this, viewModel.technicalErrorLiveData)
        errorInViewHandler.observeAuthError(this, viewModel.authErrorLiveData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(viewModel)
    }

}