package `in`.shrys.year.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.view.View
import android.widget.RemoteViews
import `in`.shrys.year.R
import `in`.shrys.year.util.DateUtils
import `in`.shrys.year.util.WidgetUpdateScheduler

/**
 * Widget provider for the YEAR widget.
 * Displays the current year's progress as a 7x53 dot grid.
 */
class YearWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        WidgetUpdateScheduler.scheduleDailyUpdate(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        WidgetUpdateScheduler.cancelDailyUpdate(context)
    }

    private fun updateWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views = RemoteViews(context.packageName, R.layout.widget_year)

        val currentYear = DateUtils.getCurrentYear()
        val dayOfYear = DateUtils.getDayOfYear()
        val daysInYear = DateUtils.getDaysInYear(currentYear)

        // Update all 371 dots (7 rows x 53 columns)
        updateDots(views, dayOfYear, daysInYear)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    /**
     * Updates the visibility and color of all dots in the grid.
     * Grid layout: 7 rows (one per weekday) x 53 columns (weeks).
     * Days are laid out column by column (week by week).
     */
    private fun updateDots(views: RemoteViews, dayOfYear: Int, daysInYear: Int) {
        for (row in 0 until ROWS) {
            for (col in 0 until COLS) {
                val dotIndex = col * ROWS + row + 1  // 1-based day number
                val dotId = getDotResourceId(row, col)

                when {
                    dotIndex > daysInYear -> {
                        // Hide dots beyond the year's length
                        views.setViewVisibility(dotId, View.INVISIBLE)
                    }
                    dotIndex <= dayOfYear -> {
                        // Past days: white dot
                        views.setViewVisibility(dotId, View.VISIBLE)
                        views.setInt(dotId, "setBackgroundResource", R.drawable.dot_white)
                    }
                    else -> {
                        // Future days: grey dot
                        views.setViewVisibility(dotId, View.VISIBLE)
                        views.setInt(dotId, "setBackgroundResource", R.drawable.dot_grey)
                    }
                }
            }
        }
    }

    /**
     * Returns the resource ID for a dot at the given row and column.
     * Dots are named dot_r{row}_c{col} in the layout.
     */
    private fun getDotResourceId(row: Int, col: Int): Int {
        return DOT_IDS[row][col]
    }

    companion object {
        const val ROWS = 7
        const val COLS = 53

        // Pre-computed dot resource IDs for performance
        // Generated programmatically in the layout as dot_r{row}_c{col}
        val DOT_IDS: Array<IntArray> by lazy {
            Array(ROWS) { row ->
                IntArray(COLS) { col ->
                    val fieldName = "dot_r${row}_c${col}"
                    try {
                        R.id::class.java.getField(fieldName).getInt(null)
                    } catch (e: Exception) {
                        0
                    }
                }
            }
        }
    }
}
