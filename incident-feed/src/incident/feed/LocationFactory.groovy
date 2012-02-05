package incident.feed

class LocationFactory {
	
	def generateLatitude (Map mapInfo) {
		Double min = mapInfo.viewport_ne_lat < mapInfo.viewport_sw_lat ? mapInfo.viewport_ne_lat:mapInfo.viewport_sw_lat
		Double max = mapInfo.viewport_ne_lat > mapInfo.viewport_sw_lat ? mapInfo.viewport_ne_lat:mapInfo.viewport_sw_lat
		
		min + (Math.random() * (max - min))
		
	}
	
	def generateLongitude (Map mapInfo) {
		Double min = mapInfo.viewport_ne_lng < mapInfo.viewport_sw_lng ? mapInfo.viewport_ne_lng:mapInfo.viewport_sw_lng
		Double max = mapInfo.viewport_ne_lng > mapInfo.viewport_sw_lng ? mapInfo.viewport_ne_lng:mapInfo.viewport_sw_lng
		
		min + (Math.random() * (max - min))
	}
	
	def generateARandomLocation (Map mapInfo) {
		Double latitude = generateLatitude (mapInfo);
		Double longitude = generateLongitude (mapInfo);
		
		Location loc = new Location ()
		loc.lat = latitude
		loc.lng = longitude
		
		GeocoderService gs = new GeocoderService()
		loc = gs.reverseGeocode (loc)
	}
	
	
}
