package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val number: String,
    val contents: List<OrderItem>
)

@Serializable
data class OrderItem(
    val item: String,
    val amount: Int,
    val price: Double
)

val orderStorage = mutableListOf<Order>(
    Order(
        number = "2020-04-06-01",
        contents = listOf(
            OrderItem(
                item = "Banana",
                amount = 2,
                price = 1.50
            ),
            OrderItem(
                item = "Water",
                amount = 1,
                price = 2.50
            ),
            OrderItem(
                item = "Cheesecake",
                amount = 3,
                price = 3.0
            )
        )
    ),
    Order(
        number = "2020-04-03-01",
        contents = listOf(
            OrderItem(
                item = "Coke",
                amount = 2,
                price = 31.50
            ),
            OrderItem(
                item = "Cheeseburger",
                amount = 12,
                price = 2.50
            ),
            OrderItem(
                item = "Ice Cream",
                amount = 3,
                price = 3.0
            )
        )
    ),
)