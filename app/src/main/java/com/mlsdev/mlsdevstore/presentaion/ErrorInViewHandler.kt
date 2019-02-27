package com.mlsdev.mlsdevstore.presentaion

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.mlsdev.mlsdevstore.R
import javax.inject.Inject

class ErrorInViewHandler @Inject
constructor() {
    private var context: Context? = null
    private var closeAppAfterError = false

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

}
