package com.codingtestmobile.currencyexchange.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Wheel Picker 컴포넌트
 *
 * 참고:
 * - https://stackoverflow.com/questions/69734451/is-there-a-way-to-create-scroll-wheel-in-jetpack-compose
 * - https://developuzzle.tistory.com/entry/Jetpack-Compose%EB%A1%9C-Scroll-Picker-%EB%A7%8C%EB%93%A4%EA%B8%B0
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> WheelPicker(
    items: List<T>,
    initialIndex: Int,
    itemHeight: Dp = 40.dp,
    visibleItemCount: Int = 3,
    onItemSelected: (index: Int, item: T) -> Unit,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
    dividerColor: Color = Color.LightGray,
    itemContent: @Composable (item: T, isSelected: Boolean) -> Unit,
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val density = LocalDensity.current
    val threshold = remember { density.run { (itemHeight / 2).toPx() } }

    // 선택된 인덱스 계산
    val selectedIndex by remember {
        derivedStateOf {
            val offset = listState.firstVisibleItemScrollOffset
            if (offset >= threshold) {
                listState.firstVisibleItemIndex + 1
            } else {
                listState.firstVisibleItemIndex
            }
        }
    }

    // 선택 변경 시 콜백 호출
    LaunchedEffect(selectedIndex) {
        if (selectedIndex in items.indices) {
            onItemSelected(selectedIndex, items[selectedIndex])
        }
    }

    // 상하단 여백
    val verticalPadding = itemHeight * (visibleItemCount / 2)

    Box(
        modifier = modifier.height(itemHeight * visibleItemCount),
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = snapFlingBehavior,
            contentPadding = PaddingValues(vertical = verticalPadding),
            modifier = Modifier.fillMaxWidth(),
        ) {
            itemsIndexed(items) { index, item ->
                Box(
                    modifier =
                        Modifier
                            .height(itemHeight)
                            .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    itemContent(item, index == selectedIndex)
                }
            }
        }

        // 선택 영역 디바이더
        if (showDivider) {
            Box(
                modifier =
                    Modifier
                        .height(itemHeight)
                        .fillMaxWidth(),
            ) {
                HorizontalDivider(
                    modifier = Modifier.align(Alignment.TopCenter),
                    thickness = 1.dp,
                    color = dividerColor,
                )
                HorizontalDivider(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    thickness = 1.dp,
                    color = dividerColor,
                )
            }
        }
    }
}

@Composable
fun <T> TextWheelPicker(
    items: List<T>,
    initialIndex: Int,
    onItemSelected: (index: Int, item: T) -> Unit,
    modifier: Modifier = Modifier,
    visibleItemCount: Int = 3,
    showDivider: Boolean = true,
    itemToString: (T) -> String = { it.toString() },
) {
    WheelPicker(
        items = items,
        initialIndex = initialIndex,
        visibleItemCount = visibleItemCount,
        onItemSelected = onItemSelected,
        modifier = modifier,
        showDivider = showDivider,
    ) { item, isSelected ->
        val textColor by animateColorAsState(
            targetValue = if (isSelected) Color.Black else Color(0xFFD0D0D0),
            label = "textColor",
        )

        Text(
            text = itemToString(item),
            fontSize = if (isSelected) 20.sp else 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = textColor,
        )
    }
}
