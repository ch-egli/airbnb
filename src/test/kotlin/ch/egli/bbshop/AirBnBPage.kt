package ch.egli.bbshop

import org.simplejavamail.api.mailer.config.TransportStrategy
import org.simplejavamail.email.EmailBuilder
import org.simplejavamail.mailer.MailerBuilder

class AirBnBPage {

    fun sendMail(title: String, url: String) {
        val eMail = EmailBuilder.startingBlank()
            .from("AirBnB Notifier", "christian.egli@gmx.net")
            .to("Christian Egli", "christian.egli@gmx.net")
            .withSubject("AirBnB Notification: $title")
            .withPlainText(url)
            .buildEmail()

        val mailer = MailerBuilder
            .withSMTPServer("mail.gmx.net", 587, "1265984", creds)
            .withTransportStrategy(TransportStrategy.SMTP)
            .buildMailer()

        mailer.sendMail(eMail);
    }

    companion object {
        private const val creds = "*****"
        const val baseUrl = "https://www.airbnb.com/s/Bruneck--Bozen--Italien/homes?tab_id=home_tab&refinement_paths%5B%5D=%2Fhomes&flexible_trip_lengths%5B%5D=one_week&price_filter_input_type=0&price_filter_num_nights=6&query=Bruneck%2C%20Bozen&place_id=ChIJXZs5jCQgeEcRKRh-7-QPkNg&date_picker_type=calendar&checkin=2022-12-26&checkout=2023-01-01&flexible_date_search_filter_type=0&adults=3&source=structured_search_input_header&search_type=user_map_move&price_min=100&price_max=350&amenities%5B%5D=4&ne_lat=46.808963590142945&ne_lng=11.976892802413005&sw_lat=46.777026010376346&sw_lng=11.89960226743986&zoom=15&search_by_map=true"
    }

}