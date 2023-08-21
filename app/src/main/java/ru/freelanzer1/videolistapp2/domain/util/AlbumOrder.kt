package ru.freelanzer1.videolistapp2.domain.util

sealed class AlbumOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): AlbumOrder(orderType)
    class Date(orderType: OrderType): AlbumOrder(orderType)
    class Color(orderType: OrderType): AlbumOrder(orderType)

    fun copy(orderType: OrderType): AlbumOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}
