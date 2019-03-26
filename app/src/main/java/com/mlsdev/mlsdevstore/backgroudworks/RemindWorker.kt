package com.mlsdev.mlsdevstore.backgroudworks

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Single

class RemindWorker(context: Context, workerParams: WorkerParameters) : RxWorker(context, workerParams) {

    override fun createWork(): Single<Result> {
        return Single.just(Result.success())
    }

}