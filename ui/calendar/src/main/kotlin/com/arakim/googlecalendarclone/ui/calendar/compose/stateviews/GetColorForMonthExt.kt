@file:Suppress("MagicNumber")

package com.arakim.googlecalendarclone.ui.calendar.compose.stateviews

import androidx.compose.ui.graphics.Color
import com.arakim.googlecalendarclone.ui.calendar.presenter.model.schedule.ScheduleMonthUiModel

@Suppress("complexity")
fun ScheduleMonthUiModel.getColorsForMonth(): List<Color> =
    when (this@getColorsForMonth.monthValue) {
        12, 1, 2 -> getColorsForWinter()
        3, 4, 5 -> getColorsForSpring()
        6, 7, 8 -> getColorsForSummer()
        9, 10, 11 -> getColorsForAutumn()
        else -> error("month: $this is not valid")
    }

private fun ScheduleMonthUiModel.getColorsForWinter(): List<Color> = listOf(
    ScheduleConsts.winterColors1.getColor(this),
    ScheduleConsts.winterColors2.getColor(this)
)

private fun ScheduleMonthUiModel.getColorsForSpring(): List<Color> = listOf(
    ScheduleConsts.springColors1.getColor(this),
    ScheduleConsts.springColors2.getColor(this)
)

private fun ScheduleMonthUiModel.getColorsForSummer(): List<Color> = listOf(
    ScheduleConsts.summerColors1.getColor(this),
    ScheduleConsts.summerColors2.getColor(this)
)

private fun ScheduleMonthUiModel.getColorsForAutumn(): List<Color> = listOf(
    ScheduleConsts.autumnColors1.getColor(this),
    ScheduleConsts.autumnColors2.getColor(this)
)

private fun List<Color>.getColor(month: ScheduleMonthUiModel): Color =
    get((month.monthValue + month.year + month.year) % size)