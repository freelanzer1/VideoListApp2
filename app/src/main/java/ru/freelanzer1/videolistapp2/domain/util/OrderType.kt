package ru.freelanzer1.videolistapp2.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
