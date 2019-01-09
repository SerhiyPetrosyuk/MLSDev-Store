package com.mlsdev.mlsdevstore.presentaion

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.mlsdev.mlsdevstore.R
import com.mlsdev.mlsdevstore.presentaion.viewmodel.BaseViewModel
import javax.inject.Inject

class ErrorInViewHandler @Inject
constructor() {
    private var context: Context? = null
    private var onNetworkErrorCallback: Observable.OnPropertyChangedCallback? = null
    private var onTechnicalErrorCallback: Observable.OnPropertyChangedCallback? = null
    private var onAuthorizationErrorCallback: Observable.OnPropertyChangedCallback? = null
    private var onCommonErrorCallback: Observable.OnPropertyChangedCallback? = null
    private var closeAppAfterError = false

    init {
        initOnPropertyChangedCallbacks()
    }

    fun setCloseAppAfterError(closeAppAfterError: Boolean) {
        this.closeAppAfterError = closeAppAfterError
    }

    fun setContext(context: Context) {
        this.context = context
    }

    fun showTechnicalError() {
        showAlertDialog(context!!.getString(R.string.error_title_base), context!!.getString(R.string.error_message_tech))
    }

    fun showNetworkError() {
        showAlertDialog(context!!.getString(R.string.error_title_base), context!!.getString(R.string.error_message_network))
    }

    fun showCommonError() {
        showAlertDialog(context!!.getString(R.string.error_title_base), context!!.getString(R.string.error_message_common))
    }

    fun showAlertDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(context!!, R.style.AlertDialogAppCompat)
                .setMessage(message)
                .setTitle(title)
                .setPositiveButton(context!!.getString(R.string.button_close), null)

        if (closeAppAfterError) {
            builder.setPositiveButton(context!!.getString(R.string.button_close)) { dialogInterface, i ->
                if (context is Activity)
                    (context as Activity).finish()
            }
        }

        builder.create()
                .show()
    }

    fun observeNetworkError(lifecycleOwner: LifecycleOwner, errorEvent: LiveData<Boolean>) {
        errorEvent.observe(lifecycleOwner, Observer {
            showNetworkError()
        })
    }

    fun observeTechError(lifecycleOwner: LifecycleOwner, errorEvent: LiveData<Boolean>) {
        errorEvent.observe(lifecycleOwner, Observer {
            showTechnicalError()
        })
    }

    fun observeCommonError(lifecycleOwner: LifecycleOwner, errorEvent: LiveData<Boolean>) {
        errorEvent.observe(lifecycleOwner, Observer {
            showCommonError()
        })
    }

    fun observeAuthError(lifecycleOwner: LifecycleOwner, errorEvent: LiveData<Boolean>) {
        errorEvent.observe(lifecycleOwner, Observer {
            if (context is Activity) {
                val intent = (context as Activity).baseContext.packageManager
                        .getLaunchIntentForPackage((context as Activity).baseContext.packageName)
                if (intent != null) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context!!.startActivity(intent)
                }
            }
        })
    }

    @Deprecated("use observeNetworkError()")
    fun subscribeNetworkErrorCallback(viewModel: BaseViewModel) {
        viewModel.networkErrorOccurred.addOnPropertyChangedCallback(onNetworkErrorCallback!!)
    }

    @Deprecated("use observeTechError()")
    fun subscribeTechErrorCallback(viewModel: BaseViewModel) {
        viewModel.technicalErrorOccurred.addOnPropertyChangedCallback(onTechnicalErrorCallback!!)
    }

    @Deprecated("use observeCommonError()")
    fun subscribeCommonErrorCallback(viewModel: BaseViewModel) {
        viewModel.commonErrorOccurred.addOnPropertyChangedCallback(onCommonErrorCallback!!)
    }

    @Deprecated("use observeAuthError()")
    fun subscribeAuthErrorCallback(viewModel: BaseViewModel) {
        viewModel.authErrorOccurred.addOnPropertyChangedCallback(onAuthorizationErrorCallback!!)
    }

    @Deprecated("observe each event separately")
    fun subscribeAllErrorCallbacks(viewModel: BaseViewModel, withAuthCallback: Boolean) {
        subscribeCommonErrorCallback(viewModel)
        subscribeTechErrorCallback(viewModel)
        subscribeNetworkErrorCallback(viewModel)

        if (withAuthCallback)
            subscribeAuthErrorCallback(viewModel)

    }

    private fun initOnPropertyChangedCallbacks() {
        onNetworkErrorCallback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, i: Int) {
                if (observable.javaClass.isAssignableFrom(ObservableBoolean::class.java))
                    showNetworkError()
            }
        }

        onTechnicalErrorCallback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, i: Int) {
                if (observable.javaClass.isAssignableFrom(ObservableBoolean::class.java))
                    showTechnicalError()
            }
        }

        onAuthorizationErrorCallback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, i: Int) {
                if (context is Activity) {
                    val intent = (context as Activity).baseContext.packageManager
                            .getLaunchIntentForPackage((context as Activity).baseContext.packageName)
                    if (intent != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        context!!.startActivity(intent)
                    }
                }

            }
        }

        onCommonErrorCallback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, i: Int) {
                if (observable.javaClass.isAssignableFrom(ObservableBoolean::class.java))
                    showCommonError()
            }
        }
    }

}
