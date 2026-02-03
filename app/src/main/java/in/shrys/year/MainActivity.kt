package `in`.shrys.year

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import `in`.shrys.year.widget.YearWidgetProvider

/**
 * Main activity that displays app info and allows adding the widget.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddWidget = findViewById<Button>(R.id.btn_add_widget)
        btnAddWidget.setOnTouchListener { v, event ->

    when (event.action) {

        android.view.MotionEvent.ACTION_DOWN -> {
            v.animate()
                .scaleX(0.96f)
                .scaleY(0.96f)
                .setDuration(80)
                .start()
        }

        android.view.MotionEvent.ACTION_UP,
        android.view.MotionEvent.ACTION_CANCEL -> {
            v.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(120)
                .start()
        }
    }

    false // important → allows click event to still trigger
}

        btnAddWidget.setOnClickListener {
            requestWidgetPin()
        }
    }

    /**
     * Requests the system to pin the YEAR widget to the home screen.
     */
    private fun requestWidgetPin() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val widgetProvider = ComponentName(this, YearWidgetProvider::class.java)

        if (appWidgetManager.isRequestPinAppWidgetSupported) {
            appWidgetManager.requestPinAppWidget(widgetProvider, null, null)
        } else {
            Toast.makeText(
                this,
                "Long press on home screen to add widgets",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
