package com.mlsdev.mlsdevstore.presentaion.product

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.databinding.ActivityProductDetailsBinding
import com.mlsdev.mlsdevstore.presentaion.BaseActivity
import com.mlsdev.mlsdevstore.presentaion.adapter.ProductGalleryAdapter
import com.mlsdev.mlsdevstore.presentaion.utils.ExtrasKeys
import javax.inject.Inject

class ProductDetailsActivity : BaseActivity() {
    @Inject
    lateinit var galleryAdapter: ProductGalleryAdapter
    lateinit var binding: ActivityProductDetailsBinding
    lateinit var viewModel: ProductDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductDetailsViewModel::class.java)
        lifecycle.addObserver(viewModel)
        binding.viewModel = viewModel
        binding.productImagesGallery.adapter = galleryAdapter
        viewModel.setProductDetailsData(intent.extras)

        viewModel.imagesLiveData.observe(this, Observer { galleryAdapter.setImages(it) })
        viewModel.descriptionStateLiveData.observe(this, Observer {
            if (it) {
                val data = Bundle().apply { putString(ExtrasKeys.KEY_PRODUCT_DESCRIPTION, viewModel.description.get()) }
                val bottomSheet = DescriptionBottomSheetFragment()
                bottomSheet.arguments = data
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            }
        })

        initErrorHandler()
        initToolbar(binding.toolbar)
        displayBackArrow(true)
    }

    private fun initErrorHandler() {
        errorInViewHandler.observeNetworkError(this, viewModel.networkErrorLiveData)
        errorInViewHandler.observeCommonError(this, viewModel.commonErrorLiveData)
        errorInViewHandler.observeTechError(this, viewModel.technicalErrorLiveData)
        errorInViewHandler.observeAuthError(this, viewModel.authErrorLiveData)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }

    companion object {

        fun launch(context: Context, bundleProductData: Bundle) {
            context.startActivity(Intent(context, ProductDetailsActivity::class.java).apply { putExtras(bundleProductData) })
        }
    }
}
