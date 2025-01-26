package com.example.kaffeeapp.presentation.main.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaffeeapp.R
import com.example.kaffeeapp.data.entities.DeliveryType
import com.example.kaffeeapp.data.entities.Drink
import com.example.kaffeeapp.presentation.main.cart.mappers.CartScreenMappers.getDiscountValueBy
import com.example.kaffeeapp.presentation.main.cart.models.CartScreenAction
import com.example.kaffeeapp.presentation.main.cart.models.InputValidationResult
import com.example.kaffeeapp.presentation.main.cart.models.OrderUi
import com.example.kaffeeapp.repository.interfaces.BranchesResult
import com.example.kaffeeapp.repository.interfaces.DataRepository
import com.example.kaffeeapp.util.Constants.ADDRESS_ADDED_SUCCESSFULLY
import com.example.kaffeeapp.util.Constants.ORDER_FAILURE
import com.example.kaffeeapp.util.Constants.ORDER_SUCCESS
import com.example.kaffeeapp.util.Constants.SUCCESS_PROMO_CODE
import com.example.kaffeeapp.util.DispatcherProvider
import com.example.kaffeeapp.util.Utils.showBadgeOnCart
import com.example.kaffeeapp.util.Validator.validateDeliveryDetail
import com.example.kaffeeapp.util.Validator.validatePhoneNumber
import com.example.kaffeeapp.util.Validator.validatePromoCode
import com.example.kaffeeapp.util.model.DrinkSize
import com.example.kaffeeapp.util.model.Resource
import com.example.kaffeeapp.util.snackbarStuff.SnackbarController
import com.example.kaffeeapp.util.snackbarStuff.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    var orderUi by mutableStateOf(OrderUi())

    var inputValidationResult by mutableStateOf(InputValidationResult())

    var orderSubmissionResult by mutableStateOf<Resource<Boolean>?>(null)

    var branches by mutableStateOf<BranchesResult>(Resource.Loading())
        private set

    init {
        viewModelScope.launch(dispatcherProvider.io) {
            branches = Resource.Loading()
            dataRepository.getBranchesDetails().collect { value ->
                branches = value
            }
        }
    }

    fun handleUserAction(action: CartScreenAction) {
        when(action) {
            is CartScreenAction.BranchSelected -> {
                setDeliveryMethod(
                    DeliveryType.BranchDelivery(
                        action.branch.name,
                        action.branch.address
                    )
                )
            }
            is CartScreenAction.DeliveryEnabledChanged -> {
                setDeliveryEnabledValue(action.isEnabled)
            }
            is CartScreenAction.NoteSaved -> {
                setNoteValue(action.note)
            }
            is CartScreenAction.OrderDeleted -> {
                removeDrinkFromCart(action.index)
            }
            is CartScreenAction.OrderSubmitted -> {
                submitOrder()
            }
            is CartScreenAction.PhoneChanged -> {
                onPhoneNumberValueChange(action.phone)
            }
            is CartScreenAction.PromoCodeApplied -> {
                setPromoCode(action.code)
            }
            is CartScreenAction.QuantityChanged -> {
                setDrinkOrderQuantity(action.index, action.newQuantity)
            }
        }
    }

    fun submitOrder() {
        if (isOrderDetailsValid()) {
            orderSubmissionResult = Resource.Loading()
            viewModelScope.launch(dispatcherProvider.io) {
                orderSubmissionResult = dataRepository.addOrder(
                    order = orderUi.toOrder()
                )
                if (orderSubmissionResult is Resource.Success) {
                    dataRepository.updateUserPoints()
                    cleanData()

                    sendSnackBarMessage(message = ORDER_SUCCESS)
                } else if (orderSubmissionResult is Resource.Failure) {
                    sendSnackBarMessage(message = ORDER_FAILURE)
                }
            }
        }
    }

    private fun isOrderDetailsValid(): Boolean {
        return inputValidationResult.isValuesValid()
    }

    fun onPhoneNumberValueChange(newValue: String) {
        orderUi = orderUi.copy(phoneNumberValue = newValue)
        inputValidationResult = inputValidationResult.copy(
            phoneErrorValue = validatePhoneNumber(newValue)
        )
    }

    fun setDeliveryEnabledValue(isEnabled: Boolean) {
        orderUi = orderUi.copy(isDeliveryEnabled = isEnabled)
    }

    fun setDeliveryMethod(address: DeliveryType) {
        orderUi = orderUi.copy(deliveryValue = address)
        inputValidationResult = inputValidationResult.copy(
            addressErrorValue = validateDeliveryDetail(address)
        )

        sendSnackBarMessage(message = ADDRESS_ADDED_SUCCESSFULLY)
    }

    fun removeDrinkFromCart(orderIndex: Int) {
        orderUi.removeOrder(orderIndex)
        calculateItemsPrice()

        if (orderUi.drinkOrders.isEmpty()) {
            cleanData()
        }
    }

    fun setDrinkOrderQuantity(index: Int, quantity: Int) {
        orderUi.setOrderQuantity(index, quantity)
        calculateItemsPrice()
    }

    private fun calculateItemsPrice() {
        val total = orderUi.calculateOrdersCost()
        orderUi = orderUi.copy(
            itemsPrice = total
        )
    }

    fun setPromoCode(value: String) {
        orderUi = orderUi.copy(promoCodeValue = value)
        val promoValue = dataRepository.getPromoCodeValue(value)
        inputValidationResult =
            inputValidationResult.copy(promoErrorValue = validatePromoCode(promoValue))
        if (promoValue != null) {
            val discountValue = orderUi.itemsPrice.getDiscountValueBy(promoValue)
            orderUi = orderUi.copy(discountValue = discountValue)

            sendSnackBarMessage(message = SUCCESS_PROMO_CODE)
        }
    }


    fun setNoteValue(value: String) {
        orderUi = orderUi.copy(note = value)
    }

    fun addDrinkToCart(drink: Drink, size: DrinkSize) {
        orderUi.addOrder(drink, size)
        showBadgeOnCart()
        calculateItemsPrice()
    }


    private fun cleanData() {
        inputValidationResult = InputValidationResult()
        orderUi = OrderUi()
    }

    private fun sendSnackBarMessage(message: String) {
        viewModelScope.launch {
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = message
                )
            )
        }
    }
}