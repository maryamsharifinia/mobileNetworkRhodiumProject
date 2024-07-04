package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.telephony.CellInfo
import android.telephony.CellInfoLte
import android.telephony.CellSignalStrengthLte
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var mapImageView: ImageView
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var locationManager: LocationManager
    private val LOCATION_PERMISSION_REQUEST_CODE = 2
    private lateinit var telephonyManager: TelephonyManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapImageView = findViewById(R.id.mapImageView)
        val loadMapButton: Button = findViewById(R.id.loadMapButton)

        loadMapButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        mapImageView.setOnTouchListener { v, event ->
            val x = event.x
            val y = event.y
            // تبدیل مختصات پیکسل به مختصات نقشه و ذخیره در پایگاه داده
            true
        }

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 10f, this)
        }

        startSignalStrengthListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedImage: Uri? = data?.data
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
            mapImageView.setImageBitmap(bitmap)
        }
    }

    override fun onLocationChanged(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude
        // به‌روزرسانی نقشه با موقعیت جدید
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                try {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 10f, this)
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startSignalStrengthListener() {
        val signalStrengthListener = object : TelephonyManager.CellInfoCallback() {
            override fun onCellInfo(cellInfo: MutableList<CellInfo>) {
                for (info in cellInfo) {
                    if (info is CellInfoLte) {
                        val lte = info.cellSignalStrength as CellSignalStrengthLte
                        val rsrp = lte.rsrp
                        val rsrq = lte.rsrq
                        // ذخیره مقادیر در پایگاه داده
                        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        if (location != null) {
                            val signalData = SignalData(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                rsrp = rsrp,
                                rsrq = rsrq,
                                timestamp = System.currentTimeMillis()
                            )
                            GlobalScope.launch {
                                AppDatabase.getDatabase(applicationContext).signalDataDao().insert(signalData)
                            }
                        }
                    }
                }
            }
        }

        telephonyManager.registerTelephonyCallback(mainExecutor, signalStrengthListener)
    }
}
