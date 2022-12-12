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
        private var bruneckCount4: Int = -1
        private var seefeldCount: Int = -1
        private var seefeldCount4: Int = -1
        private var innsbruckCount: Int = -1
        private var innsbruckCount4: Int = -1
        private var brixenCount: Int = -1
        private var brixenCount4: Int = -1
    }

    @BeforeEach
    fun setUp() {
        open(AirBnBPage.bruneckUrl)
        bruneckCount = getNumberOfHomes(AirBnBPage.nbHomesPath, "Bruneck")
        Thread.sleep(5_000)
        open(AirBnBPage.bruneckUrl4)
        bruneckCount4 = getNumberOfHomes(AirBnBPage.nbHomesPath, "Bruneck4")
        Thread.sleep(5_000)

        open(AirBnBPage.seefeldUrl)
        seefeldCount = getNumberOfHomes(AirBnBPage.nbHomesPath, "Seefeld")
        Thread.sleep(5_000)
        open(AirBnBPage.seefeldUrl4)
        seefeldCount4 = getNumberOfHomes(AirBnBPage.nbHomesPath, "Seefeld4")
        Thread.sleep(5_000)

        open(AirBnBPage.innsbruckUrl)
        innsbruckCount = getNumberOfHomes(AirBnBPage.nbHomesPath, "Innsbruck")
        Thread.sleep(5_000)
        open(AirBnBPage.innsbruckUrl4)
        innsbruckCount4 = getNumberOfHomes(AirBnBPage.nbHomesPath, "Innsbruck4")
        Thread.sleep(5_000)

        open(AirBnBPage.brixenUrl)
        brixenCount = getNumberOfHomes(AirBnBPage.nbHomesPath, "Brixen")
        Thread.sleep(5_000)
        open(AirBnBPage.brixenUrl4)
        brixenCount4 = getNumberOfHomes(AirBnBPage.nbHomesPath, "Brixen4")
        Thread.sleep(5_000)
    }

    @Test
    fun checkAvailability() {
        logger.info("${getCurrentTimestamp()} -- START")

        while (true) {
            bruneckCount = check(AirBnBPage.bruneckUrl, "Bruneck", bruneckCount)
            Thread.sleep(60_000)
            bruneckCount4 = check(AirBnBPage.bruneckUrl4, "Bruneck4", bruneckCount4)
            Thread.sleep(60_000)

            seefeldCount = check(AirBnBPage.seefeldUrl, "Seefeld", seefeldCount)
            Thread.sleep(60_000)
            seefeldCount4 = check(AirBnBPage.seefeldUrl4, "Seefeld4", seefeldCount4)
            Thread.sleep(60_000)

            innsbruckCount = check(AirBnBPage.innsbruckUrl, "Innsbruck", innsbruckCount)
            Thread.sleep(60_000)
            innsbruckCount4 = check(AirBnBPage.innsbruckUrl4, "Innsbruck4", innsbruckCount4)
            Thread.sleep(60_000)

            brixenCount = check(AirBnBPage.brixenUrl, "Brixen", brixenCount)
            Thread.sleep(60_000)
            brixenCount4 = check(AirBnBPage.brixenUrl4, "Brixen4", brixenCount4)
            Thread.sleep(60_000)
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
