package com.example.kaffeeapp.presentation.main.drinkDetails

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.data.entities.DeliveryMethod
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.data.entities.DrinkSize
import com.example.kaffeeapp.repository.interfaces.DataRepository
import com.example.kaffeeapp.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkDetailsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val drinkOrders = mutableStateListOf<DrinkOrder>()

    private var promoCode = mutableStateOf("")

    val itemsPrice = mutableStateOf("")
    val discountValue = mutableStateOf("0.0")
    val deliveryFee = mutableStateOf("0.0")

    val totalPrice by derivedStateOf {
        val itemsPrice = itemsPrice.value.toDoubleOrNull() ?: 0.0
        val discountValue = discountValue.value.toDoubleOrNull() ?: 0.0
        val deliveryFee = deliveryFee.value.toDoubleOrNull() ?: 0.0

        itemsPrice + deliveryFee - discountValue
    }

    fun createOrder(telephoneNumber: String, deliveryMethod: DeliveryMethod) {

    }

    fun removeDrinkFromCart(orderIndex: Int) {
        drinkOrders.removeAt(orderIndex)
        calculateItemsPrice()
    }

    fun setDrinkOrderQuantity(index: Int, quantity: Int) {
        val newDrinkOrder = drinkOrders[index].copy(
            quantity = quantity
        )
        drinkOrders[index] = newDrinkOrder
        calculateItemsPrice()
    }

    private fun calculateItemsPrice() {
        var total = 0.0
        drinkOrders.forEach { order ->
            val orderPrice = order.price.toDoubleOrNull() ?: 0.0
            total += order.quantity * orderPrice
        }
        itemsPrice.value = total.toString()
    }

    fun setPromoCode(value: String) {
        promoCode.value = value
    }

    private var _drink = mutableStateOf(Drink())
    val drink: State<Drink> = _drink

    fun getDrinkById(id: String) = viewModelScope.launch(dispatcherProvider.io) {
        _drink.value = dataRepository.getDrinkById(id)
    }

    fun getDrinkPrice(drinkSize: DrinkSize): String {
        return _drink.value.price[drinkSize.key].toString()
    }

    fun isDrinkFav() = dataRepository.isDrinkFav(_drink.value.id)

    fun addDrinkToFav() = viewModelScope.launch(dispatcherProvider.io) {
        dataRepository.addDrinkToFav(_drink.value.id)
    }

    fun removeDrinkFromFav() = viewModelScope.launch(dispatcherProvider.io) {
        dataRepository.removeDrinkFromFav(_drink.value.id)
    }

    fun addDrinkToCart(size: DrinkSize) {
        _drink.value.apply {
            val price = price[size.key] ?: "0"
            drinkOrders.add(
                DrinkOrder(
                    name = name,
                    id = id,
                    size = size.shortened,
                    price = price,
                    imageUrl = imageUrl,
                    quantity = 1
                )
            )
        }
        calculateItemsPrice()
    }
}