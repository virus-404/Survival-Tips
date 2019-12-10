package com.example.survivaltips.tools

import android.annotation.SuppressLint
import androidx.annotation.RequiresApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.survivaltips.R
import java.text.DecimalFormat
import kotlin.math.roundToInt


class CompassFragment : Fragment(), SensorEventListener, LocationListener {

    private lateinit var vector: Sensor
    private lateinit var sensorManager: SensorManager
    private lateinit var locationManager: LocationManager
    private lateinit var locationManager2: LocationManager
    private lateinit var cameraManager: CameraManager
    private lateinit var latitude: TextView
    private lateinit var longitude: TextView
    private lateinit var latitude2: TextView
    private lateinit var longitude2: TextView
    private lateinit var compass: ImageView
    private lateinit var compassValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager2 = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        cameraManager = activity!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        vector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    }

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.tab_compass, null) as ViewGroup
        latitude  = root.findViewById(R.id.tools_latitude_value)
        longitude = root.findViewById(R.id.tools_longitude_values)
        latitude2  = root.findViewById(R.id.tools_latitude_values_net)
        longitude2 = root.findViewById(R.id.tools_longitude_values_net)
        compass = root.findViewById(R.id.tools_compass)
        compassValue = root.findViewById(R.id.tools_compass_value)

        return root
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
            val cartesian: DoubleArray =
                CoordinatesFragment.calculateCoordinates(event.values.clone())
            compass.rotation = cartesian[2].toFloat()

            val tmp = if (cartesian[2].roundToInt() < 0) cartesian[2].roundToInt() + 360
            else cartesian[2].roundToInt()

            compassValue.text = when (tmp) {
                0 -> "N"
                in 1..89 -> "NW"
                90 -> "W"
                in 91..179 -> "SW"
                180 -> "S"
                in 181..269 -> "SE"
                270 -> "E"
                in 271..359 -> "NE"
                else -> tmp.toString()
            }


            if (tmp == 0) {
                val intent =  Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.putExtra("android.intent.extra.quickCapture",true)
                startActivityForResult(intent,100)
            } else if (tmp == 180 && cartesian[0].roundToInt() == 90) {
                for (i in 0..2) transmitDot(duration = "SHORT") //S
                for (i in 0..2) transmitDot(duration = "LONG")  //O
                for (i in 0..2) transmitDot(duration = "SHORT") //S

            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun transmitDot (duration: String) {
        if (duration == "LONG") {
            flashLightOn()
            Thread.sleep(2000)
            flashLightOff()
        } else {
            flashLightOn()
            Thread.sleep(1000)
            flashLightOff()
        }
        
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun flashLightOn() {
        val cameraManager : CameraManager =  activity!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager

        try {
            val cameraId :String = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, true)
        } catch (e : CameraAccessException) {
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun flashLightOff() {
        val cameraManager : CameraManager = activity!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val cameraId : String = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, false)
        } catch (e : CameraAccessException) {
        }
    }


    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this, vector)
        if (ActivityCompat.checkSelfPermission(this.requireContext() , android.Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) !=  PackageManager.PERMISSION_GRANTED) {
            return
        }
        locationManager.removeUpdates(this)
        locationManager2.removeUpdates(this)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, vector, SensorManager.SENSOR_DELAY_NORMAL)

        if (ActivityCompat.checkSelfPermission(this.requireContext() , android.Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this.requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) !=  PackageManager.PERMISSION_GRANTED) {
            Log.i("location", "false")
            return
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400.toLong() , 1.toFloat(), this)
        locationManager2.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400.toLong() , 1.toFloat(), this)
    }

    @SuppressLint("SetTextI18n")
    override fun onLocationChanged(location: Location?) {
        val df = DecimalFormat("#.0000000")
        if (location != null && location.provider == LocationManager.GPS_PROVIDER) {
            latitude.text  = "[GPS]: " + df.format(location.latitude).toString()
            longitude.text = "[GPS]: " + df.format(location.longitude).toString()
        } else if (location != null && location.provider == LocationManager.NETWORK_PROVIDER ) {
            latitude2.text = "[NET]: " + location.latitude.toString()
            longitude2.text = "[NET]: " + location.longitude.toString()
        }
    }



    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResult( requestCode: Int,  resultCode: Int,  data: Intent?) {
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                val photo : Bitmap =  data!!.extras!!.get("data") as Bitmap
                val popUp = PopUp(context = this.requireContext(), image = photo)
                popUp.show()
            }
        }
    }
}

// TODO: https://developer.android.com/guide/components/fragmentsz