/**
 * 
 */
package incident.feed

/**
 * @author anupam
 *
 */
class Location {
	
	String formatted_address
	String building_number
	String street1
	String street2
	String city
	String state
	String country = "US"
	
	Double lat
	Double lng
	
	String toString () {
		formatted_address + "[" + building_number + ", " + street1 + ", " + city + ", " + state + ", " + country + ", " + lat + ", " + lng  + "]"
	}
}
