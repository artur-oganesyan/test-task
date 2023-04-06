package data

data class Row(
    val customerId: Int = DataGenerator.random(),
    val customerName: String = DataGenerator.faker.name().fullName(),
    val contactName: String = DataGenerator.faker.name().fullName(),
    val address: String = DataGenerator.faker.address().streetAddress(true),
    val city: String = DataGenerator.faker.address().city(),
    val postalCode: String = DataGenerator.faker.address().zipCode(),
    val country: String = DataGenerator.faker.country().name()
)
