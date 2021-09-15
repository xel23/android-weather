package ru.mobile.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import okhttp3.*
import ru.mobile.weather.databinding.ActivityMapsBinding
import java.io.IOException

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class WeatherData(val temp: Float, val feels_like: Float)

@Serializable
data class Weather(val main: WeatherData)

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap ?: return
        mMap.setOnMapClickListener(this)
    }

    override fun onMapClick(point: LatLng) {
       Log.d(TAG, "tapped, lat=${point.latitude} lng=${point.longitude}")

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.openweathermap.org/data/2.5/weather?lat=${point.latitude}&lon=${point.longitude}&appid=53037747f36544f713dc8c1ca4691abf")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }

                    val res = Json { ignoreUnknownKeys = true }.decodeFromString<Weather>(response.body!!.string())
                    Log.d(TAG, res.main.temp.toString())
                }
            }
        })
    }
}