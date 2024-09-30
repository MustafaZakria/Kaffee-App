package com.example.kaffeeapp.util

object Constants {
    const val SPLASH_SCREEN = "splash_screen"
    const val SIGNIN_SCREEN = "signIn_screen"
    const val HOME_SCREEN = "home_screen"
    const val FAVOURITE_SCREEN = "favourite_screen"
    const val CART_SCREEN = "cart_screen"
    const val PROFILE_SCREEN = "profile_screen"
    const val DRINK_DETAIL_SCREEN = "drink_detail_screen"
    const val MAP_SCREEN = "map_screen"
    const val MY_ORDERS_SCREEN = "my_orders_screen"

    const val MAIN_GRAPH = "main_graph"
    const val AUTH_GRAPH = "auth_graph"
    const val ROOT_GRAPH = "root_graph"

    const val DRINKS_COLLECTION = "drinks"
    const val USERS_COLLECTION = "users"
    const val ORDERS_COLLECTION = "orders"

    const val FAV_DRINKS_KEY = "favouriteDrinks"
    const val ORDERS_KEY = "orders"
    const val ID_KEY = "id"
    const val NAME_KEY = "name"
    const val EMAIL_KEY = "email"
    const val IMAGE_URL_KEY = "imageUrl"

    const val PRICE_KEY = "price"
    const val DESCRIPTION_KEY = "description"
    const val INGREDIENTS_KEY = "ingredients"
    const val RATING_KEY = "rating"
    const val TYPE_KEY = "type"

    const val ORDER_ID_KEY = "orderId"
    const val UID_KEY = "uid"
    const val TIMESTAMP_KEY = "timestamp"
    const val PHONE_NUMBER_KEY = "telephoneNumber"
    const val IS_HOME_DELIVERY_KEY = "isHomeDeliveryOrder"
    const val TOTAL_PRICE_KEY = "totalPrice"
    const val NOTE_KEY = "note"
    const val DELIVERY_DETAILS_KEY = "deliveryDetails"
    const val DRINK_ORDERS_LIST_KEY = "drinkOrders"

    const val DRINK_DATABASE_NAME = "drink.db"

    const val HOME_TITLE = "Home"
    const val FAVOURITE_TITLE = "Favourite"
    const val CART_TITLE = "Cart"
    const val PROFILE_TITLE = "Notifications"

    const val KEY_SMALL_SIZE = "small"
    const val SMALL_SHORTENED = "S"
    const val KEY_MEDIUM_SIZE = "medium"
    const val MEDIUM_SHORTENED = "M"
    const val KEY_LARGE_SIZE = "large"
    const val LARGE_SHORTENED = "L"

    const val SHARED_PREFERENCE_NAME = "drinks shared pref"

    const val MAP_ZOOM = 15f

    const val ADDRESS = "address"
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
    const val BRANCH_ADDRESS = "branchAddress"

    //toasts
    const val SIGNED_IN_SUCCESSFULLY = "Signed In Successfully!"
    const val SIGNED_OUT_SUCCESSFULLY = "Signed Out Successfully!"
    const val ADDED_TO_CART_SUCCESSFULLY = "Added to Cart Successfully!"
    const val SIGNED_OUT_FAILED = "Failed to Signed Out!"
    const val NETWORK_ERROR = "Network Error!"
    const val FAILED_TO_LOAD_DATA = "Error: Failed to Load User Data!"
    const val DRINK_REMOVED_SUCCESSFULLY = "Drink Removed Successfully!"
    const val FAILED_REMOVING_DRINK = "Error: Failed to Remove the Drink!"
    const val DRINK_ADDED_SUCCESSFULLY = "Drink Added to Favourites Successfully!"
    const val ADDRESS_ADDED_SUCCESSFULLY = "Address Added Successfully!"
    const val FAILED_ADDING_DRINK = "Error: Failed to add the Drink!"
    const val ORDER_SUCCESS = "Order Success!"
}