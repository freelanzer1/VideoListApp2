package ru.freelanzer1.videolistapp2.ui.video_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.freelanzer1.videolistapp2.domain.util.AlbumOrder
import ru.freelanzer1.videolistapp2.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    albumOrder: AlbumOrder = AlbumOrder.Date(OrderType.Descending),
    onOrderChange: (AlbumOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = albumOrder is AlbumOrder.Title,
                onSelect = { onOrderChange(AlbumOrder.Title(albumOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = albumOrder is AlbumOrder.Date,
                onSelect = { onOrderChange(AlbumOrder.Date(albumOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                selected = albumOrder is AlbumOrder.Color,
                onSelect = { onOrderChange(AlbumOrder.Color(albumOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = albumOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(albumOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = albumOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(albumOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}