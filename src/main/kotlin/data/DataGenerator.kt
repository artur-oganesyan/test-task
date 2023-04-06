package data

import com.github.javafaker.Faker
import java.util.*

object DataGenerator {

    val faker = Faker(Locale("en-US"))

    fun random() = (10000..99999).random()

    fun generateRow() = Row()

}