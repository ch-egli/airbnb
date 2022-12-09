package ch.egli.bbshop

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.ex.ElementNotFound
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.simplejavamail.api.mailer.config.TransportStrategy
import org.simplejavamail.email.EmailBuilder
import org.simplejavamail.mailer.MailerBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*


class AirBnBPageTest {
    private val airBnBPage = AirBnBPage()

    companion object {
        @JvmStatic
        @BeforeAll
        fun setUpAll() {
            Configuration.browserSize = "1280x800"
            // Configuration.headless = true
        }

        private val logger: Logger = LoggerFactory.getLogger(AirBnBPageTest::class.java)
        private const val ALARM_THRESHOLD = 2
        private var isAlarmSet = false
    }

    @BeforeEach
    fun setUp() {
        open(AirBnBPage.baseUrl)
    }

    @Test
    fun checkAvailability() {
        logger.info("##### ${getCurrentTimestamp()} -- START")
        var availability = 0

        // TODO implement...
        airBnBPage.sendMail("Property found!", AirBnBPage.baseUrl)

        logger.info("##### ${getCurrentTimestamp()} -- END")
    }

    private fun getCurrentTimestamp(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.format(time) ?: ""
    }
}
