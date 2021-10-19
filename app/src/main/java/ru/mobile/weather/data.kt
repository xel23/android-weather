package ru.mobile.weather

import java.io.Serializable

data class Item(val temp: Number, val feelsLike: Number, val pressure: Number,
val humidity: Number, val name: String) : Serializable
