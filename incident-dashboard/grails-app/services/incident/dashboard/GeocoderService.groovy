package incident.dashboard

import org.codehaus.groovy.grails.web.json.JSONObject


class GeocoderService {

    static transactional = false

    def serviceMethod() {

    }
	
	def geocodeLocation(incident.dashboard.Location loc) {
		def base = "http://maps.googleapis.com/maps/api/geocode/json?address="
		def qs = []
		qs << URLEncoder.encode(loc.city+","+loc.state+","+loc.country)
		qs << "sensor=true"
		def url = new URL(base + qs.join("&"))
		def connection = url.openConnection()
	
		def map = new incident.dashboard.Map()
		if(connection.responseCode == 200){
			def rawJson = new JSONObject(connection.content.text)
			
			map.formatted_address = rawJson.results.formatted_address.get(0) as String
			map.lat = rawJson.results.geometry.location.lat.get(0) as String
			map.lng = rawJson.results.geometry.location.lng.get(0) as String
			
			map.bounds_ne_lat = rawJson.results.geometry.viewport.northeast.lat.get(0) as String
			map.bounds_ne_lng = rawJson.results.geometry.viewport.northeast.lng.get(0) as String
			map.bounds_sw_lat = rawJson.results.geometry.viewport.southwest.lat.get(0) as String
			map.bounds_sw_lng = rawJson.results.geometry.viewport.southwest.lng.get(0) as String
			
			map.viewport_ne_lat = rawJson.results.geometry.bounds.northeast.lat.get(0) as String
			map.viewport_ne_lng = rawJson.results.geometry.bounds.northeast.lng.get(0) as String
			map.viewport_sw_lat = rawJson.results.geometry.bounds.southwest.lat.get(0) as String
			map.viewport_sw_lng = rawJson.results.geometry.bounds.southwest.lng.get(0) as String
			
			
		}
		else{
			log.error("GeocoderService.geocodeLocation FAILED")
			log.error(url)
			log.error(connection.responseCode)
			log.error(connection.responseMessage)
		}
		
		return map
	}
	
}
