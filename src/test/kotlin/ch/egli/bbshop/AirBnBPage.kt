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

        val seefeldObj: UrlObj = UrlObj("Seefeld-in-Tirol--%C3%96sterreich", 6, 3, "2022-12-26", "2023-01-01", 0, 100, 320,
            "47.374022590969474",  "11.245897219890992", "47.31080246160898", "11.091316149944703")
        val seefeldUrl = seefeldObj.getUrl()

        val innsbruckObj: UrlObj = UrlObj("Innsbruck--Austria", 6, 3, "2022-12-26", "2023-01-01", 0, 100, 320,
            "47.296403965609315",  "11.492271000212895", "47.23309088884655", "11.337689930266606")
        val innsbruckUrl = innsbruckObj.getUrl()

        val brixenObj: UrlObj = UrlObj("Brixen--Bozen--Italien", 6, 3, "2022-12-26", "2023-01-01", 0, 100, 320,
            "46.75288100531197",  "11.737972005052825", "46.68892033706935", "11.583390935106536")
        val brixenUrl = brixenObj.getUrl()

        val stubaiObj: UrlObj = UrlObj("Stubaital--Gemeinde-Neustift-im-Stubaital--%C3%96sterreich", 6, 3, "2022-12-26", "2023-01-01", 0, 100, 320,
            "47.22164722316434",  "11.497129726982365", "47.09476650338078", "11.187967587089787")
        val stubaiUrl = stubaiObj.getUrl()

        // https://www.airbnb.com/s/Stubaital--Gemeinde-Neustift-im-Stubaital--%C3%96sterreich/homes?tab_id=home_tab&refinement_paths%5B%5D=%2Fhomes&flexible_trip_lengths%5B%5D=one_week&price_filter_input_type=0&price_filter_num_nights=7&query=Stubaital%2C%20Gemeinde%20Neustift%20im%20Stubaital&place_id=ChIJSTwBTeZGnUcR1idLt9c7Kd4&date_picker_type=calendar&checkin=2022-12-26&checkout=2023-01-02&adults=3&
        // source=structured_search_input_header&search_type=user_map_move&ne_lat=47.22164722316434&ne_lng=11.497129726982365&sw_lat=47.09476650338078&sw_lng=11.187967587089787&zoom=13&search_by_map=true

        val nbHomesPath = Selenide.element(Selectors.byXpath("//*[@id=\"site-content\"]/div[2]/div[3]/div/div/div/div/section/h1"))
        val noExactMatchesPath = Selenide.element(Selectors.byXpath("//*[@id=\"site-content\"]/div[2]/div[4]/div/div/div/div[1]/div/div/div/div[1]/section/h2"))
        val noExactMatchesPath2 = Selenide.element(Selectors.byXpath("//*[@id=\"site-content\"]/div[2]/div[1]/div/div/div/div[1]/div/div/div/div[1]/section/h2"))
    }
}