package `in`.shrys.year.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import `in`.shrys.year.R
import java.time.LocalDate

class YearWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        for (widgetId in appWidgetIds) {
            updateWidget(context, appWidgetManager, widgetId)
        }
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        // Scheduler will be used later
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
    }

    private fun updateWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        widgetId: Int
    ) {

        val views = RemoteViews(context.packageName, R.layout.widget_year)

        val today = LocalDate.now()
        val dayOfYear = today.dayOfYear
        val totalDays = if (today.isLeapYear) 366 else 365

        renderDots(views, dayOfYear, totalDays)

        appWidgetManager.updateAppWidget(widgetId, views)
    }

    private fun renderDots(
        views: RemoteViews,
        dayOfYear: Int,
        totalDays: Int
    ) {

        val maxDots = GRID_ROWS * GRID_COLS

        for (i in 0 until maxDots) {

            val dotId = getDotId(i)

            if (dotId == 0) continue

            when {
                i >= totalDays -> {
                    views.setViewVisibility(dotId, android.view.View.INVISIBLE)
                }

                i < dayOfYear -> {
                    views.setViewVisibility(dotId, android.view.View.VISIBLE)
                    views.setInt(
                        dotId,
                        "setBackgroundResource",
                        R.drawable.dot_white
                    )
                }

                else -> {
                    views.setViewVisibility(dotId, android.view.View.VISIBLE)
                    views.setInt(
                        dotId,
                        "setBackgroundResource",
                        R.drawable.dot_grey
                    )
                }
            }
        }
    }

    private fun getDotId(index: Int): Int {
        return try {
            val name = "dot_$index"
            R.id::class.java.getField(name).getInt(null)
        } catch (e: Exception) {
            0
        }
    }

    companion object {
        const val GRID_ROWS = 19
        const val GRID_COLS = 20
    }
}
