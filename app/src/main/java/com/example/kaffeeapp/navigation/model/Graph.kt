package com.example.kaffeeapp.navigation.model

import com.example.kaffeeapp.util.Constants.AUTH_GRAPH
import com.example.kaffeeapp.util.Constants.MAIN_GRAPH
import com.example.kaffeeapp.util.Constants.ROOT_GRAPH

sealed class Graph(val route: String) {
    data object RootGraph : Graph(ROOT_GRAPH)
    data object AuthGraph : Graph(AUTH_GRAPH)
    data object MainGraph : Graph(MAIN_GRAPH)
}