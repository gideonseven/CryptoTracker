package com.gt.cryptotracker.crypto.presentation.coin_detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign

@Composable
fun LineChart(
    dataPoints: List<DataPoint>,
    style: ChartStyle,
    visibleDataPintsIndices: IntRange,
    unit: String,
    modifier: Modifier = Modifier,
    selectedDataPoint: DataPoint? = null,
    onSelectedDataPoint: (DataPoint) -> Unit = {},
    onXLabelWidthChange: (Float) -> Unit = {},
    showHelperLines: Boolean = true
) {
    val textStyle = LocalTextStyle.current.copy(
        fontSize = style.labelFontSize
    )

    val visibleDataPoints = remember(dataPoints, visibleDataPintsIndices) {
        dataPoints.slice(visibleDataPintsIndices)
    }

    val maxYValue = remember(visibleDataPoints) {
        visibleDataPoints.maxOfOrNull { it.y } ?: 0f
    }

    val minYValue = remember(visibleDataPoints) {
        visibleDataPoints.minOfOrNull { it.y } ?: 0f
    }

    val measurer = rememberTextMeasurer()

    val xLabelWidth by remember {
        mutableFloatStateOf(0f)
    }

    LaunchedEffect(key1 = xLabelWidth) {
        onXLabelWidthChange(xLabelWidth)
    }

    val selectedDataPointIndex = remember(selectedDataPoint) {
        dataPoints.indexOf(selectedDataPoint)
    }

    var drawPoints by remember {
        mutableStateOf(listOf<DataPoint>())
    }

    var isShowingDataPoints by remember {
        mutableStateOf(selectedDataPoint != null)
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
    ) {
        val minLabelSpacingYPx = style.minYLabelSpacing.roundToPx()
        val verticalPaddingPx = style.verticalPadding.roundToPx()
        val horizontalPaddingPx = style.horizontalPadding.roundToPx()
        val xAxisLabelSpacingPx = style.xAxisLabelSpacing.roundToPx()

        //measure the text
        val xLabelTextLayoutResults = visibleDataPoints.map {
            measurer.measure(
                text = it.xLabel,
                style = textStyle.copy(textAlign = TextAlign.Center)
            )
        }

        val maxXLabelWidth = xLabelTextLayoutResults.maxOfOrNull { it.size.width } ?: 0
        val maxXLabelHeight = xLabelTextLayoutResults.maxOfOrNull { it.size.height } ?: 0
        val maxXLabelLineCount = xLabelTextLayoutResults.maxOfOrNull { it.lineCount } ?: 0
        val xLabelLineHeight = maxXLabelHeight / maxXLabelLineCount


        val viewPortHeightPx =
            size.height - (maxXLabelHeight + 2 * verticalPaddingPx + xLabelLineHeight * xAxisLabelSpacingPx)
    }
}