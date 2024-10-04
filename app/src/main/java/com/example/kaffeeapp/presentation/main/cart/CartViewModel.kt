package com.example.kaffeeapp.presentation.main.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.data.entities.DeliveryType
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.data.entities.DrinkSize
import com.example.kaffeeapp.navigation.MainScreen
import com.example.kaffeeapp.navigation.model.bottomNavItems
import com.example.kaffeeapp.presentation.main.cart.models.CartDetails
import com.example.kaffeeapp.presentation.main.cart.models.CartInputsHandler
import com.example.kaffeeapp.repository.interfaces.DataRepository
import com.example.kaffeeapp.util.Constants.FAILED_REMOVING_DRINK
import com.example.kaffeeapp.util.Constants.ORDER_SUCCESS
import com.example.kaffeeapp.util.DispatcherProvider
import com.example.kaffeeapp.util.Utils.validateDeliveryDetail
import com.example.kaffeeapp.util.Utils.validatePhoneNumber
import com.example.kaffeeapp.util.model.OrderCost
import com.example.kaffeeapp.util.model.Resource
import com.example.kaffeeapp.util.snackbarStuff.SnackbarController
import com.example.kaffeeapp.util.snackbarStuff.SnackbsrEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    var cartDetails by mutableStateOf(CartDetails())

    var cartInputsHandler by mutableStateOf(CartInputsHandler())

    var orderCost by mutableStateOf(OrderCost())

    var orderResponse by mutableStateOf<Resource<Boolean>?>(null)

    fun submitOrder() {
        if (isOrderDetailsValid()) {
            orderResponse = Resource.Loading()
            viewModelScope.launch(dispatcherProvider.io) {
                orderResponse = dataRepository.addOrder(
                    cartDetails = cartDetails,
                    totalPrice = orderCost.getTotalCost(),
                )
                onOrderResult()
            }
        }
    }

    private fun onOrderResult() {
        viewModelScope.launch {
            if (orderResponse is Resource.Success) {
                cartDetails = cartDetails.copy(drinkOrders = mutableListOf())
                SnackbarController.sendEvent(
                    SnackbsrEvent(
                        message = ORDER_SUCCESS
                    )
                )
            } else if (orderResponse is Resource.Failure) {
                SnackbarController.sendEvent(
                    SnackbsrEvent(
                        message = FAILED_REMOVING_DRINK
                    )
                )
            }
        }
    }

    private fun isOrderDetailsValid(): Boolean {
        cartInputsHandler = cartInputsHandler.copy(
            addressErrorValue = validateDeliveryDetail(cartDetails.deliveryValue),
            phoneErrorValue = validatePhoneNumber(cartDetails.phoneNumberValue)
        )
        return cartInputsHandler.isValuesValid()
    }

    fun onPhoneNumberValueChange(newValue: String) {
        cartDetails = cartDetails.copy(phoneNumberValue = newValue)
    }

    fun setDeliveryEnabledValue(isEnabled: Boolean) {
        cartDetails = cartDetails.copy(isDeliveryEnabled = isEnabled)
    }

    fun setDeliveryMethod(address: DeliveryType) {
        cartDetails = cartDetails.copy(deliveryValue = address)
    }

    fun removeDrinkFromCart(orderIndex: Int) {
        cartDetails.removeOrder(orderIndex)
        calculateItemsPrice()

        if (cartDetails.drinkOrders.isEmpty()) {
            clearStateData()
        }
    }

    fun setDrinkOrderQuantity(index: Int, quantity: Int) {
        cartDetails.setOrderQuantity(index, quantity)
        calculateItemsPrice()
    }

    private fun calculateItemsPrice() {
        val total = cartDetails.calculateOrdersCost()
        orderCost = orderCost.copy(
            itemsPrice = total
        )
    }

    fun setPromoCode(value: String) {
        cartDetails = cartDetails.copy(promoCodeValue = value)
    }

    fun setNoteValue(value: String) {
        cartDetails = cartDetails.copy(note = value)
    }

    fun addDrinkToCart(drink: Drink, size: DrinkSize) {
        cartDetails.addOrder(drink, size)
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
        cartInputsHandler = cartInputsHandler.copy(phoneErrorValue = "", addressErrorValue = "")
    }
}