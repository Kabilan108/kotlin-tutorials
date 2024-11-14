package com.example.dessertclicker.ui

import android.provider.ContactsContract.Data
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource.dessertList
import com.example.dessertclicker.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val TAG = "DesertViewModel"

data class DessertState (
    val revenue: Int = 0,
    val dessertsSold: Int = 0,
    val currentDessertIndex: Int = 0,
) {
    val currentDessertPrice: Int
        get() { return dessertList[currentDessertIndex].price }

    @get:DrawableRes
    val currentDessertImageId: Int
        get() { return dessertList[currentDessertIndex].imageId }
}

class DessertViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(DessertState())
    val uiState: StateFlow<DessertState> = _uiState.asStateFlow()

    /**
     * Determine which dessert to show.
     */
    private fun determineDessertToShow(currentDessertsSold: Int): Int {
        var dessertIDToShow = 0
        for ((idx, dessert) in dessertList.withIndex()) {
            if (currentDessertsSold >= dessert.startProductionAmount) {
                dessertIDToShow = idx
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                // you'll start producing more expensive desserts as determined by startProductionAmount
                // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
                // than the amount sold.
                break
            }
        }
        return dessertIDToShow
    }

    fun onDessertClicked() {
        Log.d(TAG, "dessert clicked: revenue = ${_uiState.value.revenue} dessertsSold = ${_uiState.value.dessertsSold}")
        _uiState.update { it ->
            val newDessertsSold = it.dessertsSold + 1
            val nextDessert = determineDessertToShow(newDessertsSold)
            it.copy(
                currentDessertIndex = nextDessert,
                revenue = it.revenue + it.currentDessertPrice,
                dessertsSold = newDessertsSold,
            )
        }
        Log.d(TAG, "state updated: revenue = ${_uiState.value.revenue} dessertsSold = ${_uiState.value.dessertsSold}")
    }
}