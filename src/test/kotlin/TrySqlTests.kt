import asserts.TrySqlPageAsserts
import data.DataGenerator
import data.Row
import io.qameta.allure.Description
import io.qameta.allure.Story
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import pages.TrySqlPage

@Story("Try SQL Page")
class TrySqlTests : TestsSettings() {

    private val trySqlPage = TrySqlPage()
    private val trySqlPageAsserts = TrySqlPageAsserts(trySqlPage)

    @Test
    @DisplayName("Get all rows")
    @Description(
        "Вывести все строки таблицы Customers и убедиться, что запись с ContactName " +
                "равной 'Giovanni Rovelli' имеет Address = 'Via Ludovico il Moro 22'."
    )
    fun getAllRows() {
        val row = trySqlPage
            .open()
            .executeSqlRequest("SELECT * FROM Customers;")
            .getRowByContactName("Giovanni Rovelli")
        trySqlPageAsserts
            .rowShouldHaveAddress(row, "Via Ludovico il Moro 22")
    }

    @Test
    @DisplayName("Get some rows")
    @Description(
        "Вывести только те строки таблицы Customers, где city='London'. " +
                "Проверить, что в таблице ровно 6 записей."
    )
    fun getSomeRows() {
        trySqlPage
            .open()
            .executeSqlRequest("SELECT * FROM Customers WHERE City = 'London';")

        trySqlPageAsserts
            .resultTableShouldHaveSize(6)
    }

    @Test
    @DisplayName("Add row")
    @Description("Добавить новую запись в таблицу Customers и проверить, что эта запись добавилась.")
    fun addRow() {
        val rowData = DataGenerator.generateRow()
        trySqlPage
            .open()
            .executeSqlRequest(
                "INSERT INTO Customers " +
                        "VALUES ('${rowData.customerId}', '${rowData.customerName}', '${rowData.contactName}', " +
                        "'${rowData.address}', '${rowData.city}', '${rowData.postalCode}' , '${rowData.country}');"
            )
        trySqlPageAsserts
            .resultShouldHaveText("You have made changes to the database. Rows affected: 1")
        val rowElement = trySqlPage
            .executeSqlRequest("SELECT * FROM Customers WHERE CustomerID = ${rowData.customerId};")
            .getRow(1)
        trySqlPageAsserts
            .resultTableShouldHaveSize(1)
            .rowShouldHaveColumns(rowElement, rowData)
    }

    @Test
    @DisplayName("Edite row")
    @Description(
        "Обновить все поля (кроме CustomerID) в любой записи таблицы Customers " +
                "и проверить, что изменения записались в базу."
    )
    fun editRow() {
        val firstRowData = trySqlPage
            .open()
            .executeSqlRequest("SELECT * FROM Customers")
            .getRowData(1)

        val newRowData = Row().copy(customerId = firstRowData.customerId)
        trySqlPage
            .executeSqlRequest("UPDATE Customers SET CustomerName = '${newRowData.customerName}', " +
                "ContactName = '${newRowData.contactName}', Address = '${newRowData.address}', City = '${newRowData.city}', " +
                "PostalCode = '${newRowData.postalCode}', Country = '${newRowData.country}' " +
                "WHERE CustomerID = ${firstRowData.customerId};"
        )
        trySqlPageAsserts
            .resultShouldHaveText("You have made changes to the database. Rows affected: 1")

        val updatedRow = trySqlPage
            .executeSqlRequest("SELECT * FROM Customers WHERE CustomerID = ${firstRowData.customerId};")
            .getRow(1)

        trySqlPageAsserts
            .resultTableShouldHaveSize(1)
            .rowShouldHaveColumns(updatedRow, newRowData)
    }

    @Test
    @DisplayName("Delete row")
    @Description("Удалить произвольное поле и убедиться что его больше нет в таблице")
    fun deleteRow() {
        val firstRowData = trySqlPage
            .open()
            .executeSqlRequest("SELECT * FROM Customers")
            .getRowData(1)

        trySqlPage
            .executeSqlRequest("DELETE FROM Customers WHERE CustomerID = ${firstRowData.customerId};")
        trySqlPageAsserts
            .resultShouldHaveText("You have made changes to the database. Rows affected: 1")

        trySqlPage
            .executeSqlRequest("SELECT * FROM Customers WHERE CustomerID = ${firstRowData.customerId};")
        trySqlPageAsserts
            .resultShouldHaveText("No result.")
    }

}

