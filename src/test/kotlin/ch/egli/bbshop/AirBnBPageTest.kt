package ch.egli.bbshop

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.SelenideElement
import com.codeborne.selenide.ex.ElementNotFound
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
        private var stubaiCount: Int = -1
        private var brixenCount: Int = -1

    }

    @BeforeEach
    fun setUp() {
        open(AirBnBPage.seefeldUrl)
        seefeldCount = getNumberOfHomes(AirBnBPage.nbHomesPath, "Seefeld")
        Thread.sleep(5_000)

        open(AirBnBPage.innsbruckUrl)
        innsbruckCount = getNumberOfHomes(AirBnBPage.nbHomesPath, "Innsbruck")
        Thread.sleep(5_000)

        open(AirBnBPage.stubaiUrl)
        stubaiCount = getNumberOfHomes(AirBnBPage.nbHomesPath, "Stubai")
        Thread.sleep(5_000)

        open(AirBnBPage.brixenUrl)
        brixenCount = getNumberOfHomes(AirBnBPage.nbHomesPath, "Brixen")
        Thread.sleep(5_000)

        open(AirBnBPage.bruneckUrl)
        bruneckCount = getNumberOfHomes(AirBnBPage.nbHomesPath, "Bruneck")
        Thread.sleep(5_000)

        airBnBPage.sendMail(
            "Links",
            "${AirBnBPage.seefeldUrl}\n\n" +
                    "${AirBnBPage.innsbruckUrl}\n\n" +
                    "${AirBnBPage.stubaiUrl}\n\n" +
                    "${AirBnBPage.brixenUrl}\n\n" +
                    "${AirBnBPage.bruneckUrl}\n\n",
            "")
    }

    @Test
    fun checkAvailability() {
        logger.info("${getCurrentTimestamp()} -- START")

        while (true) {
            try {
                seefeldCount = check(AirBnBPage.seefeldUrl, "Seefeld", seefeldCount)
                Thread.sleep(90_000)

                innsbruckCount = check(AirBnBPage.innsbruckUrl, "Innsbruck", innsbruckCount)
                Thread.sleep(90_000)

                stubaiCount = check(AirBnBPage.stubaiUrl, "Stubai", stubaiCount)
                Thread.sleep(90_000)

                brixenCount = check(AirBnBPage.brixenUrl, "Brixen", brixenCount)
                Thread.sleep(90_000)

                bruneckCount = check(AirBnBPage.bruneckUrl, "Bruneck", bruneckCount)
                Thread.sleep(90_000)
            } catch (e: Exception) {
                logger.warn("unexpected exception: ${e.message}")
            }
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
        // wait that page is completely loaded
        Thread.sleep(3_000)

        try {
            val noExactMatches = AirBnBPage.noExactMatchesPath.innerText()
            if (noExactMatches == "No exact matches") {
                logger.info("${getCurrentTimestamp()} -- $location: numberOfHomes: 0")
                return 0
            }
        } catch (ex: ElementNotFound) {
            // logger.warn("noExactMatchesPath -> not found")
        }

        try {
            val noExactMatches = AirBnBPage.noExactMatchesPath2.innerText()
            if (noExactMatches == "No exact matches") {
                logger.info("${getCurrentTimestamp()} -- $location: numberOfHomes: 0")
                return 0
            }
        } catch (ex: ElementNotFound) {
            // logger.warn("noExactMatchesPath -> not found")
        }

        try {
            val fullNbHomesText = path.innerText()
            val textWithHomes = fullNbHomesText.substringAfter("Search results")
            val numberAsText = textWithHomes.substringBefore(" home")
            logger.info("${getCurrentTimestamp()} -- $location: numberOfHomes: $numberAsText")
            return numberAsText.toIntOrNull() ?: 0
        } catch (ex: ElementNotFound) {
            logger.warn("nbHomesPath -> not found")
        }

        // indicate that an error occurred
        return -1
    }

    private fun getCurrentTimestamp(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.format(time) ?: ""
    }
}
