package com.example.marsphotos.data

import com.example.marsphotos.model.MarsPhoto

interface MarsPhotoRepository {
    suspend fun getMarsPhoto(): List<MarsPhoto>
}

class NetworkMarsPhotoRepository(
    private val marsApiService: MarsApiService
) : MarsPhotoRepository {
    override suspend fun getMarsPhoto(): List<MarsPhoto> {
        return marsApiService.getPhotos()
    }
}