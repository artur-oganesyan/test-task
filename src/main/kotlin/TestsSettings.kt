import com.codeborne.selenide.Browsers
import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.logevents.SelenideLogger
import io.qameta.allure.selenide.AllureSelenide
import io.qameta.allure.selenide.LogType
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.openqa.selenium.remote.DesiredCapabilities
import java.util.logging.Level

open class TestsSettings {

    companion object {

        @JvmStatic
        @BeforeAll
        fun setBrowser() {
            Configuration.baseUrl = "https://www.w3schools.com"
            Configuration.browser = Browsers.CHROME
            Configuration.browserSize = "1920x1080"
            Configuration.pageLoadTimeout = 120000

            if (System.getenv().containsKey("REMOTE")) {
                Configuration.remote = "http://0.0.0.0:4444/wd/hub"
                Configuration.browserCapabilities = DesiredCapabilities().apply {
                    setCapability("containerName", "TrySQL")
                    setCapability("enableVNC", true)
                    setCapability("enableVideo", false)
                }
            }

            SelenideLogger.addListener(
                "Allure Listener",
                AllureSelenide()
                    .screenshots(true)
                    .savePageSource(true)
                    .includeSelenideSteps(false)
                    .enableLogs(LogType.BROWSER, Level.ALL)
            )
        }
    }

    @AfterEach
    fun closeBrowser() {
        Selenide.closeWebDriver()
    }
}