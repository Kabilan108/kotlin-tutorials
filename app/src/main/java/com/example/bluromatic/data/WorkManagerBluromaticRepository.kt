/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bluromatic.data

import android.content.Context
import android.net.Uri
import androidx.lifecycle.asFlow
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.bluromatic.IMAGE_MANIPULATION_WORK_NAME
import com.example.bluromatic.KEY_BLUR_LEVEL
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.TAG_OUTPUT
import com.example.bluromatic.getImageUri
import com.example.bluromatic.workers.BlurWorker
import com.example.bluromatic.workers.CleanupWorker
import com.example.bluromatic.workers.SaveImageToFileWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull

class WorkManagerBluromaticRepository(context: Context) : BluromaticRepository {

//    override val outputWorkInfo: Flow<WorkInfo> = MutableStateFlow(null)

    private var imageUri: Uri = context.getImageUri()
    private val workManager = WorkManager.getInstance(context)

    /**
     * Create the WorkRequests to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    override fun applyBlur(blurLevel: Int) {
//        val continuation = workManager.beginWith(
//            OneTimeWorkRequest.from(CleanupWorker::class.java)
//        )
        // or
//        val cleanupBuilder = OneTimeWorkRequestBuilder<CleanupWorker>()
//        var continuation = workManager.beginWith(cleanupBuilder.build())
//
//        val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()
//        blurBuilder.setInputData(createInputDataForWorkRequest(blurLevel, imageUri))
////        workManager.enqueue(blurBuilder.build())
//        continuation = continuation.then(blurBuilder.build())
//
//        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>().build()
//        continuation = continuation.then(save)
        // even better:
        val cleanup= OneTimeWorkRequestBuilder<CleanupWorker>()
            .build()
        val blur= OneTimeWorkRequestBuilder<BlurWorker>()
            .setInputData(createInputDataForWorkRequest(blurLevel, imageUri))
            .build()
        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
            .addTag(TAG_OUTPUT)
            .build()

//        val continuation = workManager.beginWith(cleanup)
//            .then(blur)
//            .then(save)

        // use beginUniqueWork if you want only a single instance of this chain at a time
        // the entire chain will be named so it can be referenced as a single unit
        // pass ExistingWorkPolicy to tell andorid what to do if another worker exists
        val continuation = workManager.beginUniqueWork(
            IMAGE_MANIPULATION_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            cleanup
        )
            .then(blur)
            .then(save)
        continuation.enqueue()
    }

    /**
     * Cancel any ongoing WorkRequests
     * */
    override fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }

    override val outputWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData(TAG_OUTPUT).asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first() else null
        }

    /**
     * to track job status in the UI:
     *
     * getWorkInfoByIdLiveData()  -> returns single LiveData<WorkInfo> for a specific work request
     * getWorkInfoForUniqueWorkLiveData() -> returns LiveData<List<WorkInfo>> for all workers in a chain
     * getWorkInfoByTagLiveData() -> returns LiveData<List<WorkInfo>> for a specific tag
     *
     * we convert LiveData APIs to flows
    */

    /**
     * Creates the input data bundle which includes the blur level to
     * update the amount of blur to be applied and the Uri to operate on
     * @return Data which contains the Image Uri as a String and blur level as an Integer
     */
    private fun createInputDataForWorkRequest(blurLevel: Int, imageUri: Uri): Data {
        val builder = Data.Builder()
        builder.putString(KEY_IMAGE_URI, imageUri.toString()).putInt(KEY_BLUR_LEVEL, blurLevel)
        return builder.build()
    }
}
