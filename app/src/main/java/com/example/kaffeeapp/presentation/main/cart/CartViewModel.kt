package com.example.kaffeeapp.presentation.main.cart

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
import com.example.kaffeeapp.util.model.OrderCost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val drinkOrders = mutableStateListOf<DrinkOrder>()

    var deliveryMethod = mutableStateOf<DeliveryMethod?>(null)

    private var promoCodeState = mutableStateOf("")

    private val phoneNumberState = mutableStateOf("")

    val isDeliveryEnabled = mutableStateOf(true)

    val orderCost = mutableStateOf(OrderCost())

    val isPhoneNumberValid = mutableStateOf(true)
    val isAddressNull = mutableStateOf(true)

    val totalPrice by derivedStateOf {
        val itemsPrice = orderCost.value.itemsPrice.toDoubleOrNull() ?: 0.0
        val discountValue = orderCost.value.discountValue.toDoubleOrNull() ?: 0.0
        val deliveryFee = orderCost.value.deliveryFee.toDoubleOrNull() ?: 0.0

        itemsPrice + deliveryFee - discountValue
    }

    fun createOrder() {
        if(isOrderDetailsValid()) {
            viewModelScope.launch(dispatcherProvider.io) {
                deliveryMethod.value?.let {
                    dataRepository.addOrder(
                        drinkOrders = drinkOrders,
                        phoneNumber = phoneNumberState.value,
                        totalPrice = totalPrice.toString(),
                        isHomeDelivery = isDeliveryEnabled.value,
                        deliveryDetail = it
                    )
                }
            }
        }
    }

    private fun isOrderDetailsValid(): Boolean {
        if(deliveryMethod.value == null){
            isAddressNull.value = true
            return false
        }
        if(phoneNumberState.value == "" || phoneNumberState.value.length < 10) {
            isPhoneNumberValid.value = false
            return false
        }
        return true
    }

    fun onPhoneNumberValueChange(newValue: String) {
        phoneNumberState.value = newValue
    }

    fun setDeliveryEnabledValue(isEnabled: Boolean) {
        isDeliveryEnabled.value = isEnabled
    }

    fun setDeliveryMethod(address: DeliveryMethod) {
        deliveryMethod.value = address
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

        orderCost.value = orderCost.value.copy(
            itemsPrice = total.toString()
        )
    }

    fun setPromoCode(value: String) {
        promoCodeState.value = value
    }


    fun addDrinkToCart(drink: Drink, size: DrinkSize) {
        drink.apply {
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