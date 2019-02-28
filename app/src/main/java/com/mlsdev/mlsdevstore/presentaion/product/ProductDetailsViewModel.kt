package com.mlsdev.mlsdevstore.presentaion.product

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.data.DataSource
import com.mlsdev.mlsdevstore.data.cart.Cart
import com.mlsdev.mlsdevstore.data.model.item.Image
import com.mlsdev.mlsdevstore.data.model.item.Item
import com.mlsdev.mlsdevstore.presentaion.utils.CustomObservableBoolean
import com.mlsdev.mlsdevstore.presentaion.utils.Utils
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import javax.inject.Inject

class ProductDetailsViewModel @Inject
constructor(
        context: Context,
        dataSource: DataSource,
        utils: Utils,
        private val cart: Cart
) : BaseViewModel(), Cart.OnMaxItemsReachedListener, Cart.OnItemAddedListener {

    private var item: Item? = null
    val title = ObservableField<String>()
    val imageUrl = ObservableField<String>()
    val price = ObservableField<String>()
    val currency = ObservableField<String>()
    val feedbackScore = ObservableField("0.0")
    val feedbackPercent = ObservableField("0.0")
    @Deprecated("use `val descriptionStateLiveData`")
    val descriptionIsDisplayed = CustomObservableBoolean()
    val descriptionStateLiveData = MutableLiveData<Boolean>()
    val description = ObservableField<String>()
    val condition = ObservableField<String>()
    val brand = ObservableField<String>()
    val size = ObservableField<String>()
    val gender = ObservableField<String>()
    val color = ObservableField<String>()
    val material = ObservableField<String>()
    val imagesLiveData = MutableLiveData<List<Image>>()

    init {
        this.context = context
        this.dataSource = dataSource
        this.utils = utils
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    internal fun start() {
        cart.addOnMaxItemsReachedListener(this)
        cart.addOnItemAddedListener(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    internal fun stop() {
        cart.removeOnMaxItemsReachedListener(this)
        cart.removeOnItemAddedListener(this)
    }

    fun setProductDetailsData(productDetailsData: Bundle?) {
        if (productDetailsData == null)
            return

        ProductFragmentArgs.fromBundle(productDetailsData).productDetails.let {
            item = it
            title.set(it.title)
            imageUrl.set(it.imageUrl)
            price.set(it.price.value.toString())
            currency.set(it.price.currency)
            condition.set(it.condition)
            retrieveDetailedInfo(it.id)
        }
    }

    private fun retrieveDetailedInfo(itemId: String) {
        checkNetworkConnection(utils!!) {
            compositeDisposable.add(dataSource!!.getItem(itemId).subscribe(
                    { product: Item ->
                        description.set(product.description)
                        brand.set(product.brand)
                        size.set(product.size)
                        gender.set(product.gender)
                        color.set(product.color)
                        material.set(product.material)
                        feedbackScore.set(product.seller.feedbackScore.toString())
                        feedbackPercent.set(product.seller.getFeedbackRating())
                        imageUrl.set(product.imageUrl)
                        imageUrl.notifyChange()

                        val images = product.additionalImages
                        if (images.isEmpty())
                            images.add(product.image)

                        imagesLiveData.postValue(product.additionalImages)
                    },
                    { handleError(it) }
            ))
        }
    }

    fun onAddToCartClicked(button: View) {
        cart.addItem(item)
    }

    fun onDescriptionClick() {
        descriptionStateLiveData.postValue(true)
        descriptionIsDisplayed.set(true)
    }

    override fun onMaxItemsReached() {
        Toast.makeText(context, R.string.message_item_count_restriction, Toast.LENGTH_SHORT).show()
    }

    override fun onItemAdded(item: Item) {
        Toast.makeText(context, R.string.message_item_added, Toast.LENGTH_SHORT).show()
    }
}
