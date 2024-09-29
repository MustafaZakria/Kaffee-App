package com.example.kaffeeapp.presentation.main.cart

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.data.entities.DeliveryMethod
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkOrder
import com.example.kaffeeapp.data.entities.DrinkSize
import com.example.kaffeeapp.navigation.MainScreen
import com.example.kaffeeapp.navigation.model.bottomNavItems
import com.example.kaffeeapp.repository.interfaces.DataRepository
import com.example.kaffeeapp.util.DispatcherProvider
import com.example.kaffeeapp.util.model.OrderCost
import com.example.kaffeeapp.util.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val drinkOrders = mutableStateListOf<DrinkOrder>()

    private var promoCodeState = mutableStateOf("")

    var deliveryMethod = mutableStateOf<DeliveryMethod?>(null)
    val isDeliveryEnabled = mutableStateOf(true)
    val isAddressNull = mutableStateOf<Boolean?>(null)

    val orderCost = mutableStateOf(OrderCost())

    val isPhoneNumberValid = mutableStateOf<Boolean?>(null)
    val phoneNumberState = mutableStateOf("")

    val totalPrice by derivedStateOf {
        val itemsPrice = orderCost.value.itemsPrice.toDoubleOrNull() ?: 0.0
        val discountValue = orderCost.value.discountValue.toDoubleOrNull() ?: 0.0
        val deliveryFee = orderCost.value.deliveryFee.toDoubleOrNull() ?: 0.0

        itemsPrice + deliveryFee - discountValue
    }

    var orderResult by mutableStateOf<Resource<Boolean>?>(null)

    fun submitOrder() {
        if (isOrderDetailsValid()) {
            orderResult = Resource.Loading()
            viewModelScope.launch(dispatcherProvider.io) {
                orderResult = dataRepository.addOrder(
                    drinkOrders = drinkOrders,
                    phoneNumber = phoneNumberState.value,
                    totalPrice = totalPrice.toString(),
                    isHomeDelivery = isDeliveryEnabled.value,
                    deliveryDetail = deliveryMethod.value
                )
                if (orderResult is Resource.Success) {
                    drinkOrders.clear()
                }
            }
        }
    }


    private fun isOrderDetailsValid(): Boolean {
        isAddressNull.value = deliveryMethod.value == null
        isPhoneNumberValid.value = phoneNumberState.value.isNotBlank() &&
                phoneNumberState.value.length > 10

        return !(isAddressNull.value)!! && (isPhoneNumberValid.value!!)
    }

    fun onPhoneNumberValueChange(newValue: String) {
        phoneNumberState.value = newValue
    }

    fun setDeliveryEnabledValue(isEnabled: Boolean) {
        isDeliveryEnabled.value = isEnabled
    }

    fun setDeliveryMethod(address: DeliveryMethod) {
        deliveryMethod.value = address
        isAddressNull.value = false
    }

    fun removeDrinkFromCart(orderIndex: Int) {
        drinkOrders.removeAt(orderIndex)
        calculateItemsPrice()

        if (drinkOrders.isEmpty()) {
            clearStateData()
        }
    }

    fun setDrinkOrderQuantity(index: Int, quantity: Int) {
        val newDrinkOrder = drinkOrders[index].copy(
            quantity = quantity
        )
        drinkOrders[index] = newDrinkOrder
        calculateItemsPrice()
    }

    private fun calculateItemsPrice() {
        var total = drinkOrders.sumOf { it.quantity * (it.price.toDoubleOrNull() ?: 0.0) }

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
        showBadgeOnCart()
        calculateItemsPrice()
    }

    private fun showBadgeOnCart() {
        bottomNavItems.first { it.route == MainScreen.CartScreen.route }.hasUpdate.value = true
    }

    fun removeBadgeOnCart() {
        bottomNavItems.first { it.route == MainScreen.CartScreen.route }.hasUpdate.value = false
    }

    private fun clearStateData() {
        isPhoneNumberValid.value = null
        isAddressNull.value = null
    }

    fun resetOrderResponseState() {
        orderResult = null
    }
}