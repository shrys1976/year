package `in`.shrys.year.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.*
import android.widget.RemoteViews
import `in`.shrys.year.R
import java.time.LocalDate

class YearWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (id in appWidgetIds) {
            updateWidget(context, appWidgetManager, id)
        }
    }

    private fun updateWidget(
        context: Context,
        manager: AppWidgetManager,
        widgetId: Int
    ) {

        val views = RemoteViews(context.packageName, R.layout.widget_year)

        val bitmap = drawYearBitmap()

        views.setImageViewBitmap(R.id.dot_bitmap, bitmap)

        manager.updateAppWidget(widgetId, views)
    }

private fun drawYearBitmap(): Bitmap {

    val width = 800
    val height = 800   // square = nicer grid

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val today = LocalDate.now()
    val dayOfYear = today.dayOfYear
    val totalDays = if (today.isLeapYear) 366 else 365

    val rows = 20
    val cols = 19   // 20x19 = 380 capacity

    val dotPaintPast = Paint().apply {
        color = Color.WHITE
        isAntiAlias = true
    }

    val dotPaintFuture = Paint().apply {
        color = Color.parseColor("#666666")
        isAntiAlias = true
    }

    val spacingX = width / cols.toFloat()
    val spacingY = height / rows.toFloat()

    val dotRadius = minOf(spacingX, spacingY) * 0.3f

    var index = 0

    for (row in 0 until rows) {
        for (col in 0 until cols) {

            if (index >= totalDays) continue

            val x = col * spacingX + spacingX / 2
            val y = row * spacingY + spacingY / 2

            val paint =
                if (index < dayOfYear) dotPaintPast
                else dotPaintFuture

            canvas.drawCircle(x, y, dotRadius, paint)

            index++
        }
    }

    return bitmap
}

}
