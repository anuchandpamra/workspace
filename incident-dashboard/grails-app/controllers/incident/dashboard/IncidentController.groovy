package incident.dashboard

class IncidentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [incidentInstanceList: Incident.list(params), incidentInstanceTotal: Incident.count()]
    }

    def create = {
        def incidentInstance = new Incident()
        incidentInstance.properties = params
        return [incidentInstance: incidentInstance]
    }

    def save = {
        def incidentInstance = new Incident(params)
        if (incidentInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'incident.label', default: 'Incident'), incidentInstance.id])}"
            redirect(action: "show", id: incidentInstance.id)
        }
        else {
            render(view: "create", model: [incidentInstance: incidentInstance])
        }
    }

    def show = {
        def incidentInstance = Incident.get(params.id)
        if (!incidentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'incident.label', default: 'Incident'), params.id])}"
            redirect(action: "list")
        }
        else {
            [incidentInstance: incidentInstance]
        }
    }

    def edit = {
        def incidentInstance = Incident.get(params.id)
        if (!incidentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'incident.label', default: 'Incident'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [incidentInstance: incidentInstance]
        }
    }

    def update = {
        def incidentInstance = Incident.get(params.id)
        if (incidentInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (incidentInstance.version > version) {
                    
                    incidentInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'incident.label', default: 'Incident')] as Object[], "Another user has updated this Incident while you were editing")
                    render(view: "edit", model: [incidentInstance: incidentInstance])
                    return
                }
            }
            incidentInstance.properties = params
            if (!incidentInstance.hasErrors() && incidentInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'incident.label', default: 'Incident'), incidentInstance.id])}"
                redirect(action: "show", id: incidentInstance.id)
            }
            else {
                render(view: "edit", model: [incidentInstance: incidentInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'incident.label', default: 'Incident'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def incidentInstance = Incident.get(params.id)
        if (incidentInstance) {
            try {
                incidentInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'incident.label', default: 'Incident'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'incident.label', default: 'Incident'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'incident.label', default: 'Incident'), params.id])}"
            redirect(action: "list")
        }
    }
}
