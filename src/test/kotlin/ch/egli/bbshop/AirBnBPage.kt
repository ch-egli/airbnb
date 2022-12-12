package ch.egli.bbshop

import com.codeborne.selenide.Selectors
import com.codeborne.selenide.Selenide
import org.simplejavamail.api.mailer.config.TransportStrategy
import org.simplejavamail.email.EmailBuilder
import org.simplejavamail.mailer.MailerBuilder

class AirBnBPage {

    fun sendMail(title: String, text: String, url: String) {
        val eMail = EmailBuilder.startingBlank()
            .from("AirBnB Notifier", "christian.egli@gmx.net")
            .to("Christian Egli", "christian.egli@gmx.net")
            .withSubject("AirBnB Notification: $title")
            .withPlainText(text + "\n\n" + url)
            .buildEmail()

        val mailer = MailerBuilder
            .withSMTPServer("mail.gmx.net", 587, "1265984", creds)
            .withTransportStrategy(TransportStrategy.SMTP)
            .buildMailer()

        mailer.sendMail(eMail);
    }

    companion object {
        private const val creds = "*****"

        val bruneckObj: UrlObj = UrlObj("Bruneck--Bozen--Italien", 6, 3, "2022-12-26", "2023-01-01", 0, 100, 320,
            "46.822249911309676",  "12.042355160078472", "46.75837157428507", "11.887774090132183")
        val bruneckUrl = bruneckObj.getUrl()
        val bruneckObj4: UrlObj = UrlObj("Bruneck--Bozen--Italien", 6, 4, "2022-12-26", "2023-01-01", 0, 100, 320,
            "46.822249911309676",  "12.042355160078472", "46.75837157428507", "11.887774090132183")
        val bruneckUrl4 = bruneckObj4.getUrl()

        val seefeldObj: UrlObj = UrlObj("Seefeld-in-Tirol--%C3%96sterreich", 6, 3, "2022-12-26", "2023-01-01", 0, 100, 320,
            "47.374022590969474",  "11.245897219890992", "47.31080246160898", "11.091316149944703")
        val seefeldUrl = seefeldObj.getUrl()
        val seefeldObj4: UrlObj = UrlObj("Seefeld-in-Tirol--%C3%96sterreich", 6, 4, "2022-12-26", "2023-01-01", 0, 100, 320,
            "47.374022590969474",  "11.245897219890992", "47.31080246160898", "11.091316149944703")
        val seefeldUrl4 = seefeldObj4.getUrl()

        val innsbruckObj: UrlObj = UrlObj("Innsbruck--Austria", 6, 3, "2022-12-26", "2023-01-01", 0, 100, 320,
            "47.296403965609315",  "11.492271000212895", "47.23309088884655", "11.337689930266606")
        val innsbruckUrl = innsbruckObj.getUrl()
        val innsbruckObj4: UrlObj = UrlObj("Innsbruck--Austria", 6, 4, "2022-12-26", "2023-01-01", 0, 100, 320,
            "47.296403965609315",  "11.492271000212895", "47.23309088884655", "11.337689930266606")
        val innsbruckUrl4 = innsbruckObj4.getUrl()

        val brixenObj: UrlObj = UrlObj("Brixen--Bozen--Italien", 6, 3, "2022-12-26", "2023-01-01", 0, 100, 320,
            "46.75288100531197",  "11.737972005052825", "46.68892033706935", "11.583390935106536")
        val brixenUrl = brixenObj.getUrl()
        val brixenObj4: UrlObj = UrlObj("Brixen--Bozen--Italien", 6, 4, "2022-12-26", "2023-01-01", 0, 100, 320,
            "46.75288100531197",  "11.737972005052825", "46.68892033706935", "11.583390935106536")
        val brixenUrl4 = brixenObj4.getUrl()

        val nbHomesPath = Selenide.element(Selectors.byXpath("//*[@id=\"site-content\"]/div[2]/div[3]/div/div/div/div/section/h1"))
        val noExactMatchesPath = Selenide.element(Selectors.byXpath("//*[@id=\"site-content\"]/div[2]/div[4]/div/div/div/div[1]/div/div/div/div[1]/section/h2"))
    }
}