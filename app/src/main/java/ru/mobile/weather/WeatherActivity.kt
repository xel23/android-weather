package ru.mobile.weather

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather)

        val item = intent.getSerializableExtra("item") as Item

        val temp = findViewById<TextView>(R.id.temperature)
        val feelsLike = findViewById<TextView>(R.id.feelsLike)
        val pressure = findViewById<TextView>(R.id.pressure)
        val humidity = findViewById<TextView>(R.id.humidity)

        temp.text = item.temp.toString()
        feelsLike.text = item.feelsLike.toString()
        pressure.text = item.pressure.toString()
        humidity.text = item.humidity.toString()
    }
}