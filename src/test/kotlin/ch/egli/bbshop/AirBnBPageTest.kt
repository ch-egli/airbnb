package ch.egli.bbshop

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.SelenideElement
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.util.*


class AirBnBPageTest {
    private val airBnBPage = AirBnBPage()

    companion object {
        @JvmStatic
        @BeforeAll
        fun setUpAll() {
            // Configuration.browserSize = "2560x1440"
            Configuration.headless = true
        }

        private val logger: Logger = LoggerFactory.getLogger(AirBnBPageTest::class.java)

        private var bruneckCount: Int = -1
        private var seefeldCount: Int = -1
        private var innsbruckCount: Int = -1
    }

    @BeforeEach
    fun setUp() {
        open(AirBnBPage.bruneckUrl)
        bruneckCount = getNumberOfHomes(AirBnBPage.nbHomesPath, "Bruneck")
        Thread.sleep(5_000)

        open(AirBnBPage.seefeldUrl)
        seefeldCount = getNumberOfHomes(AirBnBPage.nbHomesPath, "Seefeld")
        Thread.sleep(5_000)

        open(AirBnBPage.innsbruckUrl)
        innsbruckCount = getNumberOfHomes(AirBnBPage.nbHomesPath, "Innsbruck")
        Thread.sleep(5_000)
    }

    @Test
    fun checkAvailability() {
        logger.info("${getCurrentTimestamp()} -- START")

        while (true) {
            bruneckCount = check(AirBnBPage.bruneckUrl, "Bruneck", bruneckCount)
            Thread.sleep(20_000)

            seefeldCount = check(AirBnBPage.seefeldUrl, "Seefeld", seefeldCount)
            Thread.sleep(20_000)

            innsbruckCount = check(AirBnBPage.innsbruckUrl, "Innsbruck", innsbruckCount)
            Thread.sleep(20_000)
        }

        // logger.info("${getCurrentTimestamp()} -- END")
    }

    private fun check(url: String, location: String, currentCount: Int): Int {
        open(url)
        val numberOfHomes = getNumberOfHomes(AirBnBPage.nbHomesPath, location)
        if (numberOfHomes != currentCount) {
            airBnBPage.sendMail("$location!", "Count was $currentCount, but is now $numberOfHomes", url)
        }
        return numberOfHomes
    }

    private fun getNumberOfHomes(path: SelenideElement, location: String): Int {
        var fullNbHomesText = "Search resultsOver 1,000 stays"
        while (fullNbHomesText.contains("Search resultsOver 1,000 stays")) {
            Thread.sleep(1_000)
            fullNbHomesText = path.innerText()
            logger.debug("${getCurrentTimestamp()} -- $location: fullNbHomesText: $fullNbHomesText")
        }

        val textWithHomes = fullNbHomesText.substringAfter("Search results")
        val numberAsText = textWithHomes.substringBefore(" homes")
        logger.info("${getCurrentTimestamp()} -- $location: numberOfHomes: $numberAsText")
        return numberAsText.toIntOrNull() ?: 0
    }

    private fun getCurrentTimestamp(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.format(time) ?: ""
    }
}
