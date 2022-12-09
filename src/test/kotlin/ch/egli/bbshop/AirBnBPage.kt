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
            "46.855756194296085",  "12.107586483836712", "46.72423781964254", "11.798424343944134")
        val bruneckUrl = bruneckObj.getUrl()

        val seefeldObj: UrlObj = UrlObj("Seefeld-in-Tirol--%C3%96sterreich", 6, 3, "2022-12-26", "2023-01-01", 0, 100, 320,
            "47.374022590969474",  "11.245897219890992", "47.31080246160898", "11.091316149944703")
        val seefeldUrl = seefeldObj.getUrl()

        val innsbruckObj: UrlObj = UrlObj("Innsbruck--Austria", 6, 3, "2022-12-26", "2023-01-01", 0, 100, 320,
            "47.296403965609315",  "11.492271000212895", "47.23309088884655", "11.337689930266606")
        val innsbruckUrl = innsbruckObj.getUrl()

        val nbHomesPath = Selenide.element(Selectors.byXpath("//*[@id=\"site-content\"]/div[2]/div[3]/div/div/div/div/section/h1"))
    }
}