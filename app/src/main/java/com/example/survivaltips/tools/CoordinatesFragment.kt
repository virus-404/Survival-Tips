package com.example.survivaltips.tools

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.survivaltips.R
import kotlin.math.*


class CoordinatesFragment : Fragment(), SensorEventListener {

    private lateinit var accelerometer: Sensor
    private lateinit var vector: Sensor
    private lateinit var sensorManager: SensorManager
    private lateinit var x : TextView
    private lateinit var y : TextView
    private lateinit var z : TextView
    private lateinit var position: TextView
    
    private val limit = 0.2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        sensorManager = activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        vector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.tab_coordinate, null) as ViewGroup
        x = root.findViewById(R.id.tools_x_value)
        y = root.findViewById(R.id.tools_y_value)
        z = root.findViewById(R.id.tools_z_value)
        position = root.findViewById(R.id.tools_position_value)

        x.text = "0"
        y.text = "0"
        z.text = "0"

        return root
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            if (event.values[0].minus(x.text.toString().toDouble()).absoluteValue > limit) x.text = event.values[0].toString()

            if (event.values[1].minus(y.text.toString().toDouble()).absoluteValue > limit) y.text = event.values[1].toString()

            if (event.values[2].minus(z.text.toString().toDouble()).absoluteValue > limit) z.text = event.values[2].toString()

        } else if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {

            val cartesian = calculateCoordinates(event.values.clone())
            var pos = ""

            pos += when {
                cartesian[0].absoluteValue < 90 -> "Up"
                cartesian[0].absoluteValue > 90 -> "Down"
                else -> "Stand up "
            }

            pos += " "

            pos += when {
                cartesian[1] < 0 -> "Left"
                cartesian[1] > 0 -> "Right"
                else -> "screen"
            }

            position.text = pos

            val lp: WindowManager.LayoutParams = this.activity!!.window.attributes
            var brightness: Float? = null

            if (cartesian[0].roundToInt() == 0) brightness = 0.0f
            else if (cartesian[0].roundToInt() == 90) brightness = 1.0f
            if (brightness != null) {
                lp.screenBrightness = brightness
                this.activity!!.window.attributes = lp
            } else if (pos == "Down Left" || pos == "Down Right") {
                this.activity!!.finishAffinity()
            }

        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this, accelerometer)
        sensorManager.unregisterListener(this, vector)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, vector, SensorManager.SENSOR_DELAY_NORMAL)
    }

    companion object {
        fun calculateCoordinates (input: FloatArray): DoubleArray {
            val pitch: Double
            val tilt: Double
            val azimuth: Double

            //Get Rotation Vector Sensor Values
            val g: DoubleArray = convertFloatsToDoubles(input)

            //Normalise
            val norm = sqrt(
                g[0] * g[0] + g[1] * g[1] + g[2] * g[2] + g[3] * g[3]
            )
            g[0] /= norm
            g[1] /= norm
            g[2] /= norm
            g[3] /= norm

            //Set values to commonly known quaternion letter representatives
            val x = g[0]
            val y = g[1]
            val z = g[2]
            val w = g[3]

            //Calculate Pitch in degrees (-180 to 180)
            val sinP = 2.0 * (w * x + y * z)
            val cosP = 1.0 - 2.0 * (x * x + y * y)
            pitch = atan2(sinP, cosP) * (180 / Math.PI)

            //Calculate Tilt in degrees (-90 to 90)
            val sinT = 2.0 * (w * y - z * x)
            tilt = if (abs(sinT) >= 1) (Math.PI / 2).withSign(sinT) * (180 / Math.PI)
            else asin(sinT) * (180 / Math.PI)

            //Calculate Azimuth in degrees (0 to 360; 0 = North, 90 = East, 180 = South, 270 = West)
            val sinA = 2.0 * (w * z + x * y)
            val cosA = 1.0 - 2.0 * (y * y + z * z)
            azimuth = atan2(sinA, cosA) * (180 / Math.PI)

            return doubleArrayOf(pitch,tilt,azimuth)
        }

        private fun convertFloatsToDoubles(input: FloatArray): DoubleArray {
            val output = DoubleArray(input.size)
            for (i in input.indices) output[i] = input[i].toDouble()
            return output

        }


    }

}

//TODO: https://stackoverflow.com/questions/11175599/how-to-measure-the-tilt-of-the-phone-in-xy-plane-using-accelerometer-in-android/15149421#15149421
// TODO: https://developer.android.com/guide/components/fragments