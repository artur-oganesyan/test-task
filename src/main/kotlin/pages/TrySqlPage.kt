package pages

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.SelenideElement
import data.Row
import io.qameta.allure.Step
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import utils.JSExecutor

class TrySqlPage {

    private val sqlInputWrapper = Selenide.element(".CodeMirror-wrap")
    private val sqlInputTextArea = sqlInputWrapper.find(By.tagName("textarea"))
    private val runSqlButton = Selenide.element(".ws-btn")
    val result = Selenide.element("[id='divResultSQL']")
    private val resultTable = result.find(By.tagName("table"))

    @Step("Open TrySql page")
    fun open(): TrySqlPage {
        Selenide.open("/sql/trysql.asp?filename=trysql_select_all")
        return this
    }

    @Step("Fill SQL input: {text}")
    fun fillSqlInput(text: String): TrySqlPage {
        sqlInputWrapper.click()
        sqlInputTextArea.sendKeys(text)
        JSExecutor().insertText(text)
        return this
    }

    @Step("Clear SQL input")
    fun clearSqlInput(): TrySqlPage {
        sqlInputWrapper.click()
        repeat(sqlInputWrapper.text().length) {
            sqlInputTextArea.sendKeys(Keys.BACK_SPACE)
        }
        return this
    }

    @Step("Click button 'Run SQL'")
    fun clickRunSqlButton(): TrySqlPage {
        runSqlButton.click()
        return this
    }

    @Step("Execute SQL request: {text}")
    fun executeSqlRequest(text: String): TrySqlPage {
        clearSqlInput()
        fillSqlInput(text)
        clickRunSqlButton()
        return this
    }

    @Step("Find row by CustomerID: {customerId}")
    fun getRowByCustomerId(customerId: Int): SelenideElement = getAllRowsFromTable()
        .single {
            it.findAll(By.tagName("td"))[0].text == customerId.toString()
        }

    @Step("Find row by ContactName: {contactName}")
    fun getRowByContactName(contactName: String): SelenideElement = getAllRowsFromTable()
        .single {
            it.findAll(By.tagName("td"))[2].text == contactName
        }

    /**
     * Note that the first row is the header.
     */
    @Step("Get row number {index}")
    fun getRow(index: Int) = resultTable
        .shouldBe(Condition.visible)
        .findAll(By.tagName("tr"))[index]

    /**
     * Note that the first row is the header.
     */
    @Step("Get row number {index}")
    fun getRowData(index: Int): Row {
        val columns = getRow(index)
            .findAll(By.tagName(if (index == 0) "th" else "td")) // Because first row is header
            .map { it.text() }
        return Row(columns[0].toInt(), columns[1], columns[2], columns[3], columns[4], columns[5], columns[6])
    }

    /**
     * Return only rows, without header
     */
    fun getAllRowsFromTable() = resultTable
            .shouldBe(Condition.visible)
            .findAll(By.tagName("tr"))
            .drop(1) // Deleted first row because is header

}
