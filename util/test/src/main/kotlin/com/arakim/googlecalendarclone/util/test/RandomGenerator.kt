package com.arakim.googlecalendarclone.util.test

import java.util.UUID
import kotlin.random.Random

fun randomInt(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Int =
    (min..max).random()

fun randomLong(min: Long = Long.MIN_VALUE, max: Long = Long.MAX_VALUE): Long =
    (min..max).random()

fun randomDouble(min: Double = Double.MIN_VALUE, max: Double = Double.MAX_VALUE): Double =
    Random.nextDouble(min, max)

fun randomFloat(min: Float = Float.MIN_VALUE, max: Float = Float.MAX_VALUE): Float =
    randomDouble(min.toDouble(), max.toDouble()).toFloat()

fun randomString() = UUID.randomUUID().toString()

fun randomBoolean(): Boolean = Random.nextBoolean()
