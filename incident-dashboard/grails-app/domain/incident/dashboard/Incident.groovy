 package incident.dashboard

class Incident {

    static constraints = {
    }
	static belongsTo = [location:Location]
	String description
	Date time_of
}
