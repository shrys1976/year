package `in`.shrys.year.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.widget.RemoteViews
import `in`.shrys.year.R
import `in`.shrys.year.util.WidgetUpdateScheduler
import java.time.LocalDate

class YearWidgetProvider : AppWidgetProvider() {

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        WidgetUpdateScheduler.scheduleDailyUpdate(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        WidgetUpdateScheduler.cancelDailyUpdate(context)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        WidgetUpdateScheduler.scheduleDailyUpdate(context)
        for (id in appWidgetIds) {
            updateWidget(context, appWidgetManager, id)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_MY_PACKAGE_REPLACED,
            Intent.ACTION_DATE_CHANGED,
            Intent.ACTION_TIME_CHANGED,
            Intent.ACTION_TIMEZONE_CHANGED -> {
                WidgetUpdateScheduler.scheduleDailyUpdate(context)
                updateAllWidgets(context)
            }
        }
    }

    private fun updateAllWidgets(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val componentName = ComponentName(context, YearWidgetProvider::class.java)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)
        onUpdate(context, appWidgetManager, appWidgetIds)
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
        val height = 800

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val today = LocalDate.now()
        val dayOfYear = today.dayOfYear
        val totalDays = if (today.isLeapYear) 366 else 365

        val rows = 20
        val cols = 19

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
                if (index >= totalDays) {
                    break
                }

                val x = col * spacingX + spacingX / 2
                val y = row * spacingY + spacingY / 2

                val paint = if (index < dayOfYear) dotPaintPast else dotPaintFuture

                canvas.drawCircle(x, y, dotRadius, paint)

                index++
            }

            if (index >= totalDays) {
                break
            }
        }

        return bitmap
    }

}
