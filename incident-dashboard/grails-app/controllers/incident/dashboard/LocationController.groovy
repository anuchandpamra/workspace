package incident.dashboard

import grails.converters.JSON
import groovy.sql.Sql;

class LocationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def geocoderService
	def dataSource
	
    def index = {
        redirect(action: "list", params: params)
    }
	
	def map = {
		def locationInstance = new Location(params)
		def map = geocoderService.geocodeLocation(locationInstance)
		[map : map]
	}

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [locationInstanceList: Location.list(params), locationInstanceTotal: Location.count()]
    }

    def create = {
        def locationInstance = new Location()
        locationInstance.properties = params
        return [locationInstance: locationInstance]
    }

    def save = {
        def locationInstance = new Location(params)
        if (locationInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'location.label', default: 'Location'), locationInstance.id])}"
            redirect(action: "show", id: locationInstance.id)
        }
        else {
            render(view: "create", model: [locationInstance: locationInstance])
        }
    }

    def show = {
        def locationInstance = Location.get(params.id)
        if (!locationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'location.label', default: 'Location'), params.id])}"
            redirect(action: "list")
        }
        else {
            [locationInstance: locationInstance]
        }
    }

    def edit = {
        def locationInstance = Location.get(params.id)
        if (!locationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'location.label', default: 'Location'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [locationInstance: locationInstance]
        }
    }

    def update = {
        def locationInstance = Location.get(params.id)
        if (locationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (locationInstance.version > version) {
                    
                    locationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'location.label', default: 'Location')] as Object[], "Another user has updated this Location while you were editing")
                    render(view: "edit", model: [locationInstance: locationInstance])
                    return
                }
            }
            locationInstance.properties = params
            if (!locationInstance.hasErrors() && locationInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'location.label', default: 'Location'), locationInstance.id])}"
                redirect(action: "show", id: locationInstance.id)
            }
            else {
                render(view: "edit", model: [locationInstance: locationInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'location.label', default: 'Location'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def locationInstance = Location.get(params.id)
        if (locationInstance) {
            try {
                locationInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'location.label', default: 'Location'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'location.label', default: 'Location'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'location.label', default: 'Location'), params.id])}"
            redirect(action: "list")
        }
    }
	
	def incidents = {
		
		Sql sql = new Sql(dataSource);
		
		def incidents = []
		
		def qry = """
		SELECT i.id,
  i.description,
  TO_CHAR(i.time_of, 'DD-MON-YYYY HH24:MI:SSxFF') AS incident_time,
  l.formatted_address,
  l.building_number,
  l.street1,
  l.street2,
  l.city,
  l.state,
  l.country,
  l.lat,
  l.lng
FROM INCIDENT i,
  LOCATION l
WHERE i.time_of > CAST((systimestamp - TO_DSINTERVAL('0 0:2:0.00')) AS TIMESTAMP)
AND to_number(l.lat) BETWEEN ${params.sw_lat} AND ${params.ne_lat}
AND to_number(l.lng) BETWEEN ${params.sw_lng} AND ${params.ne_lng}
AND i.location_id = l.id
		"""
		sql.eachRow (qry, 
		{incidents << ["id":it.id, 
			"description": it.description,
			"incident_time":it.incident_time,
			"formatted_address":it.formatted_address, 
			"lat":it.lat,
			"lng" : it.lng] });
		
		def incidentMap = ["incidents":incidents]

		println incidentMap
		
		render incidentMap as JSON
	}
	
	def tester = {
		
	}
}
