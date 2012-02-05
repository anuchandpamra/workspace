package incident.feed

class GeocoderService {

	def geocodeLocation(incident.feed.Location loc) {
		def base = "http://maps.googleapis.com/maps/api/geocode/xml?address="
		def qs = []
		qs << URLEncoder.encode(loc.city+","+loc.state+","+loc.country)
		qs << "sensor=false"
		def url = new URL(base + qs.join("&"))
		def connection = url.openConnection()
	
		def map = new incident.feed.Map()
		if(connection.responseCode == 200){
			def xml = connection.content.text
			def geoCodeResponse = new XmlSlurper().parseText(xml)
	
			
			map.formatted_address = geoCodeResponse.result.formatted_address as String
			map.lat = (geoCodeResponse.result.geometry.location.lat as String) as Double
			map.lng = (geoCodeResponse.result.geometry.location.lng as String) as Double
			
			map.bounds_ne_lat = (geoCodeResponse.result.geometry.viewport.northeast.lat as String) as Double
			map.bounds_ne_lng = (geoCodeResponse.result.geometry.viewport.northeast.lng as String) as Double
			map.bounds_sw_lat = (geoCodeResponse.result.geometry.viewport.southwest.lat as String) as Double
			map.bounds_sw_lng = (geoCodeResponse.result.geometry.viewport.southwest.lng as String) as Double
			
			map.viewport_ne_lat = (geoCodeResponse.result.geometry.bounds.northeast.lat as String) as Double
			map.viewport_ne_lng = (geoCodeResponse.result.geometry.bounds.northeast.lng as String) as Double
			map.viewport_sw_lat = (geoCodeResponse.result.geometry.bounds.southwest.lat as String) as Double
			map.viewport_sw_lng = (geoCodeResponse.result.geometry.bounds.southwest.lng as String) as Double
			
		}
		else{
			log.error("GeocoderService.geocodeLocation FAILED")
			log.error(url)
			log.error(connection.responseCode)
			log.error(connection.responseMessage)
		}
		
		return map
	}
	
	
	def reverseGeocode(Location loc){
		def base = "http://maps.googleapis.com/maps/api/geocode/xml?latlng="
		def qs = []
		qs << URLEncoder.encode(loc.lat+","+loc.lng)
		qs << "sensor=false"
		def url = new URL(base + qs.join("&"))
		def connection = url.openConnection()
		if(connection.responseCode == 200){
			def xml = connection.content.text
			def geoCodeResponse = new XmlSlurper().parseText(xml)
			
			loc.formatted_address = geoCodeResponse.result[0].formatted_address as String
			geoCodeResponse.result[0].address_component.each {
					
					if (it.type == 'street_number')
						loc.building_number = it.long_name as String
					if (it.type == 'route')
						loc.street1 = it.long_name as String
					if (it.type == 'localitypolitical')
						loc.city = it.long_name as String
					if (it.type == 'administrative_area_level_1political')
						loc.state = it.short_name as String
				}
			
		}
		return loc;
	}
}
