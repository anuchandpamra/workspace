package incident.dashboard

class Location {

    static constraints = {
		city(blank:false, maxSize:100)
		state(blank:false, maxSize:100)
		country()
    }
	
	static hasMany = [incidents: Incident]
	
	String formatted_address
	String building_number
	String street1
	String street2
	String city
	String state
	String country = "US"
	
	String lat
	String lng
}
