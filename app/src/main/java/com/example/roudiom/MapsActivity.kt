package com.example.roudiom
import com.example.roudiom.R  // Adjust package name as per your project


import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker



class MapsActivity : AppCompatActivity() {

    private lateinit var map: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
//        Configuration.getInstance().load(this, androidx.preference.PreferenceManager.getDefaultSharedPreferences(this))


        map = findViewById(R.id.map)
        map.setMultiTouchControls(true)
        val mapController = map.controller
        mapController.setZoom(15.0)
        val startPoint = GeoPoint(48.8583, 2.2944)
        mapController.setCenter(startPoint)


        loadAndDisplayData()
    }

    private fun loadAndDisplayData() {
        GlobalScope.launch {
            val db = AppDatabase.getDatabase(applicationContext)
            val allData = db.signalDataDao().getAllSignalData()
            for (data in allData) {
                runOnUiThread {
                    val point = GeoPoint(data.latitude, data.longitude)
                    val marker = Marker(map)
                    marker.position = point
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                    val color = when {
                        data.rsrp > -85 -> Color.GREEN
                        data.rsrp > -100 -> Color.YELLOW
                        data.rsrp > -115 -> Color.LTGRAY
                        data.rsrp > -130 -> Color.RED
                        else -> Color.BLACK
                    }
                    marker.icon = getColoredDrawable(color)

                    map.overlays.add(marker)
                }
            }
        }
    }



    private fun getColoredDrawable(color: Int): Drawable? {
        val drawable = ContextCompat.getDrawable(this, org.osmdroid.library.R.drawable.marker_default)
        drawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        return drawable
    }

}
