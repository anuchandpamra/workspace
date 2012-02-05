package incident.feed

import groovy.sql.Sql


class IncidentFactory {
	
	def sql = Sql.newInstance("jdbc:oracle:thin:@bluesheeva_dbserver:1521:devdb", "grails",
		"Lu!Jul11", "oracle.jdbc.driver.OracleDriver")

	
	def policeIncidents =['Robbery', 'Burglary', 'Larceny', 'Explosive Device or Threat',
		'Assault', 'Child Abuse', 'Other Crime', 'Disorderly Person/Group or Noise',
		'Security Alarm', 'Roving band', 'Dispute', 'Vehicle Accident', 'Ambulance Case',
		'Ambulance Case No RMP required', 'Outstretched Person', 'Alarm or Fire'];
	Random rand = new Random();
	LocationFactory lf = new LocationFactory()
	
	def generateIncidents (Map mapInfo){
		Location loc = lf.generateARandomLocation(mapInfo)
		
		Incident incident = new Incident ()
		
		incident.loc = loc
		incident.description = policeIncidents[rand.nextInt(16)]
		
		persistIncident(incident)
		
		return incident
	}
	
	def persistIncident(Incident incident){
		def nextVal = sql.firstRow("select HIBERNATE_SEQUENCE.NEXTVAL from DUAL")
		
		def delQry = """
		DELETE
FROM location
WHERE id IN
  (SELECT l.id
  FROM location l,
    incident i
  WHERE l.id    = i.location_id
  AND i.time_of < CAST((systimestamp - TO_DSINTERVAL('0 0:10:0.00')) AS TIMESTAMP)
  )
		""";
		
		sql.execute(delQry);
		
		sql.execute("insert into location (id, version, building_number, street1, street2, city, state, country, lat, lng, formatted_address) values (?,?,?,?,?,?,?,?,?,?,?)",
			[nextVal.NEXTVAL, 0, incident.loc.building_number, incident.loc.street1, incident.loc.street2, incident.loc.city, incident.loc.state, incident.loc.country, incident.loc.lat, incident.loc.lng, incident.loc.formatted_address]);
		sql.execute("insert into incident (id, version, location_id, description) values (?,?,?,?)" ,[nextVal.NEXTVAL, 0, nextVal.NEXTVAL, incident.description]);
		
		
		
	}
}
