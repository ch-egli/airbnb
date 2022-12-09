package ch.egli.bbshop

class UrlObj(
    val location: String,
    var numNights: Int,
    var adults: Int,
    var checkin: String,
    var checkout: String,
    var flexDate: Int,
    var priceMin: Int,
    var priceMax: Int,
    var neLat: String,
    var neLng: String,
    var swLat: String,
    var swLng: String
) {
    fun getUrl(): String {
        return "https://www.airbnb.com/s/$location/homes?refinement_paths%5B%5D=%2Fhomes&price_filter_num_nights=${numNights}&checkin=${checkin}&checkout=${checkout}&flexible_date_search_filter_type=${flexDate}&adults=${adults}&price_min=${priceMin}&price_max=${priceMax}&amenities%5B%5D=4&ne_lat=${neLat}&ne_lng=${neLng}&sw_lat=${swLat}&sw_lng=${swLng}"
    }
}


