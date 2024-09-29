package com.example.kaffeeapp.presentation.main.cart

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
import com.example.kaffeeapp.util.model.CartDetails
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

    private var promoCodeState by mutableStateOf("")

    var cartUiDetails by mutableStateOf(CartDetails())

    var orderCost by mutableStateOf(OrderCost())

    var orderResult by mutableStateOf<Resource<Boolean>?>(null)

    fun submitOrder() {
        if (isOrderDetailsValid()) {
            orderResult = Resource.Loading()
            viewModelScope.launch(dispatcherProvider.io) {
                orderResult = dataRepository.addOrder(
                    drinkOrders = drinkOrders,
                    phoneNumber = cartUiDetails.phoneNumberValue,
                    totalPrice = orderCost.getTotalCost(),
                    isHomeDelivery = cartUiDetails.isDeliveryEnabled,
                    deliveryDetail = cartUiDetails.deliveryMethod
                )
                if (orderResult is Resource.Success) {
                    drinkOrders.clear()
                }
            }
        }
    }

    private fun isOrderDetailsValid(): Boolean {
        val isDeliverMethodValid = cartUiDetails.deliveryMethod != null
        val isPhoneNumberValid = cartUiDetails.phoneNumberValue.isNotBlank() &&
                cartUiDetails.phoneNumberValue.length > 10

        cartUiDetails = cartUiDetails.copy(
            isPhoneNumberValid = isPhoneNumberValid,
            isAddressNull = !isDeliverMethodValid
        )
        return isDeliverMethodValid && isPhoneNumberValid
    }

    fun onPhoneNumberValueChange(newValue: String) {
        cartUiDetails = cartUiDetails.copy(phoneNumberValue = newValue)
    }

    fun setDeliveryEnabledValue(isEnabled: Boolean) {
        cartUiDetails = cartUiDetails.copy(isDeliveryEnabled = isEnabled)
    }

    fun setDeliveryMethod(address: DeliveryMethod) {
        cartUiDetails = cartUiDetails.copy(deliveryMethod = address, isAddressNull = false)
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
        val total = drinkOrders.sumOf { it.quantity * (it.price.toDoubleOrNull() ?: 0.0) }
        orderCost = orderCost.copy(
            itemsPrice = total.toString()
        )
    }

    fun setPromoCode(value: String) {
        promoCodeState = value
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
        cartUiDetails = cartUiDetails.copy(isAddressNull = null, isPhoneNumberValid = null)
    }

    fun resetOrderResponseState() {
        orderResult = null
    }
}