package asserts

import com.codeborne.selenide.Condition
import com.codeborne.selenide.SelenideElement
import data.Row
import io.qameta.allure.Step
import org.junit.jupiter.api.Assertions
import org.openqa.selenium.By
import pages.TrySqlPage

class TrySqlPageAsserts(private val trySqlPage: TrySqlPage) {

    @Step("Result table should have size: {size}")
    fun resultTableShouldHaveSize(size: Int): TrySqlPageAsserts {
        val testRows = trySqlPage.getAllRowsFromTable()
        val testSize = testRows.size

        Assertions.assertEquals(
            size,
            testSize,
            "Result table size not equals: $size"
        )
        return this
    }

    @Step("Row should have address: {address}")
    fun rowShouldHaveAddress(row: SelenideElement, address: String): TrySqlPageAsserts {
        row.findAll(By.tagName("td"))[3].shouldHave(Condition.text(address))
        return this
    }

    @Step(
        "Row should have columns: {row.customerId}, {row.customerName}, {row.contactName}, " +
                "{row.address}, {row.city}, {row.postalCode}, {row.country}"
    )
    fun rowShouldHaveColumns(rowElement: SelenideElement, rowData: Row): TrySqlPageAsserts {
        val columns = rowElement.findAll(By.tagName("td"))
        columns[0].shouldHave(Condition.text(rowData.customerId.toString()))
        columns[1].shouldHave(Condition.text(rowData.customerName))
        columns[2].shouldHave(Condition.text(rowData.contactName))
        columns[3].shouldHave(Condition.text(rowData.address))
        columns[4].shouldHave(Condition.text(rowData.city))
        columns[5].shouldHave(Condition.text(rowData.postalCode))
        columns[6].shouldHave(Condition.text(rowData.country))
        return this
    }

    @Step("Result should have text: {text}")
    fun resultShouldHaveText(text: String) {
        trySqlPage.result.shouldHave(Condition.text(text))
    }

}